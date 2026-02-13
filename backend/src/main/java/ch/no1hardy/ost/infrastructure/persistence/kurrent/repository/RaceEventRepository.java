package ch.no1hardy.ost.infrastructure.persistence.kurrent.repository;

import ch.no1hardy.ost.infrastructure.persistence.kurrent.event.RaceEvent;
import io.kurrent.dbclient.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class RaceEventRepository {
    private final KurrentDBClient client;
    private final JsonMapper jsonMapper = new JsonMapper();
    private final String streamName = RaceEvent.class.getSimpleName();

    public RaceEvent save(RaceEvent entity) {
        byte[] json = jsonMapper.writeValueAsBytes(entity);
        EventData eventData = EventData.builderAsJson(streamName, json).build();
        try {
            client.appendToStream(streamName, eventData).get();
            return findLast();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public RaceEvent findLast() {
        try {
            RecordedEvent event = client.readStream(streamName, ReadStreamOptions.get().backwards().fromEnd().maxCount(1)).get().getEvents().stream()
                    .map(ResolvedEvent::getEvent)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Entity not found"));
            return jsonMapper.readValue(event.getEventData(), RaceEvent.class);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
