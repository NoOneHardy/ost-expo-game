package ch.no1hardy.ost.infrastructure.persistence.kurrent.event;

import ch.no1hardy.ost.domain.model.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RaceEvent {
    private UUID id;
    private LocalDateTime startTimestamp;
    private List<Participant> participants = new ArrayList<>();
}
