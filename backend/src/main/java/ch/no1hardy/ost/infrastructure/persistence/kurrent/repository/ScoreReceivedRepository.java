package ch.no1hardy.ost.infrastructure.persistence.kurrent.repository;

import ch.no1hardy.ost.infrastructure.persistence.kurrent.event.ScoreReceived;
import io.kurrent.dbclient.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Repository
@RequiredArgsConstructor
public class ScoreReceivedRepository {
    private final KurrentDBClient client;
    private final JsonMapper jsonMapper = new JsonMapper();
    private final String streamName = ScoreReceived.class.getSimpleName();

    public void save(ScoreReceived entity) {
        byte[] json = jsonMapper.writeValueAsBytes(entity);
        EventData eventData = EventData.builderAsJson(streamName, json).build();

        try {
            client.appendToStream(streamName, eventData).get();
            findById(entity.id());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ScoreReceived> findAll() {
        try {
            return client.readStream(streamName, ReadStreamOptions.get())
                    .thenApply(result -> result.getEvents().stream()
                            .map(ResolvedEvent::getEvent)
                            .map(RecordedEvent::getEventData)
                            .map(bytes -> jsonMapper.readValue(bytes, ScoreReceived.class))
                            .toList())
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            return List.of();
        }
    }

    public List<ScoreReceived> listSince(UUID id) {
        List<ScoreReceived> all = findAll();
        int index = all.indexOf(findById(id).orElseThrow());
        return all.subList(index + 1, all.size());
    }

    public Optional<ScoreReceived> findById(UUID id) {
        return findAll().stream()
                .filter(scoreReceived -> scoreReceived.id().equals(id))
                .findFirst();
    }

    public boolean isEventInLast(UUID id, int n) {
        List<ScoreReceived> all = findAll();
        if (all.size() < n) return all.stream().anyMatch(scoreReceived -> scoreReceived.id().equals(id));
        return findAll().subList(all.size() - n, all.size()).stream()
                .anyMatch(scoreReceived -> scoreReceived.id().equals(id));
    }
}