package ch.no1hardy.ost.infrastructure.persistence.mongo.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Scoreboard {
    private String id;
}
