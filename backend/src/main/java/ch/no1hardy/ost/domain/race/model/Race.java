package ch.no1hardy.ost.domain.race.model;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record Race(
        UUID id,
        LocalDateTime start,
        LocalDateTime end,
        Optional<String> email,
        String username,
        boolean hasHighRisk,
        boolean hasIssue
) {
}
