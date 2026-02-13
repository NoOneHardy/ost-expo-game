package ch.no1hardy.ost.infrastructure.persistence.kurrent.repository;

import ch.no1hardy.ost.infrastructure.persistence.kurrent.event.ScoreReceived;
import io.kurrent.dbclient.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class ScoreReceivedRepository {
    private final KurrentDBClient client;
    private final JsonMapper jsonMapper = new JsonMapper();
    private final String streamName = ScoreReceived.class.getSimpleName();

    public ScoreReceived save(ScoreReceived entity) {
        byte[] json = jsonMapper.writeValueAsBytes(entity);
        EventData eventData = EventData.builderAsJson(streamName, json).build();
        System.out.println(Arrays.toString(eventData.getEventData()));
        try {
            client.appendToStream(streamName, eventData).get();
            return findLast();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public ScoreReceived findLast() {
        try {
            RecordedEvent event = client.readStream(streamName, ReadStreamOptions.get().forwards().fromStart().maxCount(10)).get().getEvents().stream()
                    .map(ResolvedEvent::getEvent)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Entity not found"));
            return jsonMapper.readValue(event.getEventData(), ScoreReceived.class);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}