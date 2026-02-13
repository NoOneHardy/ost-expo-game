package ch.no1hardy.ost.application.race.dto.out;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record RaceDto(
        UUID id,
        LocalDateTime start,
        LocalDateTime end,
        Optional<String> username,
        String email,
        boolean hasHighRisk,
        boolean hasIssue
) {
}
