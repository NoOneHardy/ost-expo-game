package ch.no1hardy.ost.domain.scoreboard.model;

import java.util.Optional;
import java.util.UUID;

public record Score(
        UUID id,
        Optional<String> name,
        Integer score
) {
}
