package ch.no1hardy.ost.presentation.rest.scoreboard;

import ch.no1hardy.ost.application.scoreboard.dto.out.ScoreboardDto;
import ch.no1hardy.ost.application.scoreboard.port.in.LoadScoreboardUseCase;
import ch.no1hardy.ost.infrastructure.mapper.scoreboard.ScoreboardDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoadScoreboardController {
    private final ScoreboardDtoMapper scoreboardDtoMapper;
    private final LoadScoreboardUseCase loadScoreboardService;

    @GetMapping("/scoreboard")
    public ScoreboardDto loadScoreboard() {
        return scoreboardDtoMapper.toDto(loadScoreboardService.loadScoreboard());
    }
}
