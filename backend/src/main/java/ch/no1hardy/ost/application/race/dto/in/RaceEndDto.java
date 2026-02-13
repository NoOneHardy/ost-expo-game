package ch.no1hardy.ost.application.race.dto.in;

import java.util.Optional;

public record RaceEndDto(
        boolean hasHighRisk,
        boolean hasIssue,
        String email,
        Optional<String> username
) {
}
