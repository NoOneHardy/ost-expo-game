package ch.no1hardy.ost.domain.model;

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
public class Race {
    private UUID id;
    private LocalDateTime startTimestamp;
    private List<Participant> participants = new ArrayList<>();
}
