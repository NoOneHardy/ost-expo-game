package ch.no1hardy.ost.infrastructure.persistence.mongo.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Document
public class ScoreboardDocument {
    private String id;
    private String latestId;
    private List<ScoreDocument> scores;

    public ScoreboardDocument() {
        setId(UUID.randomUUID().toString());
    }
}
