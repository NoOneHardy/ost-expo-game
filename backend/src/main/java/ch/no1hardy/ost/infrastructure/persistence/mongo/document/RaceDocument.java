package ch.no1hardy.ost.infrastructure.persistence.mongo.document;

import ch.no1hardy.ost.domain.model.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "races")
public class RaceDocument {
    @Id
    private UUID id;
    private LocalDateTime startTimestamp;
    private List<Participant> participants = new ArrayList<>();
}
