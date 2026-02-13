package ch.no1hardy.ost.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participant {
    private UUID id;
    private String email;
    private RiskDecision riskDecision;
    private Boolean problemsOccurred;
    private LocalDateTime endTimestamp;
}
