package ch.no1hardy.ost.application.user.dto.in;

import java.util.Optional;

public record RaceEndDto(
        boolean hasHighRisk,
        boolean hasIssue,
        String email,
        Optional<String> username
) {
}
