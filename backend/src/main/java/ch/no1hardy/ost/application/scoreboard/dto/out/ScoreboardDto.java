package ch.no1hardy.ost.application.scoreboard.dto.out;

import java.util.List;

public record ScoreboardDto(
        List<ScoreDto> scores
) {
}
