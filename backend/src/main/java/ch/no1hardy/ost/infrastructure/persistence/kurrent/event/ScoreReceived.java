package ch.no1hardy.ost.infrastructure.persistence.kurrent.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ScoreReceived {
    private UUID id;
    private String playerName;
    private Integer score;
    private LocalDateTime timestamp;
}
