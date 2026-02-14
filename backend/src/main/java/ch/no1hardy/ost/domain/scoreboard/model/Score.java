package ch.no1hardy.ost.domain.scoreboard.model;

import java.util.UUID;

public record Score(
        UUID id,
        String name,
        Integer score
) {
}
