package ch.no1hardy.ost.infrastructure.persistence.kurrent.event;

import java.util.Optional;
import java.util.UUID;

public record ScoreReceived(
        UUID id,
        Optional<String> playerName,
        Integer score
) {
}
