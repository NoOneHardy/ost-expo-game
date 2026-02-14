package ch.no1hardy.ost.application.scoreboard.service;

import ch.no1hardy.ost.application.scoreboard.port.in.BuildScoreboardUseCase;
import ch.no1hardy.ost.application.scoreboard.port.in.LoadScoreboardUseCase;
import ch.no1hardy.ost.domain.scoreboard.model.Scoreboard;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoadScoreboardService implements LoadScoreboardUseCase {
    private final BuildScoreboardUseCase buildScoreboardService;

    @Override
    public Scoreboard loadScoreboard() {
        return buildScoreboardService.buildScoreboard();
    }
}
