package ch.no1hardy.ost.infrastructure.persistence.kurrent.event;

import java.util.UUID;

public record ScoreReceived(
        UUID id,
        String playerName,
        Integer score
) {
}
