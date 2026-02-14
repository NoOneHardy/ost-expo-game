package ch.no1hardy.ost.application.scoreboard.service;

import ch.no1hardy.ost.application.scoreboard.port.in.BuildScoreboardUseCase;
import ch.no1hardy.ost.application.scoreboard.port.in.CreateScoreboardSnapshotUseCase;
import ch.no1hardy.ost.application.scoreboard.port.out.CheckForSnapshotRepoPort;
import ch.no1hardy.ost.application.scoreboard.port.out.StoreScoreboardSnapshotRepoPort;
import ch.no1hardy.ost.domain.scoreboard.model.Scoreboard;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateScoreboardSnapshotService implements CreateScoreboardSnapshotUseCase {
    private final BuildScoreboardUseCase buildScoreboardService;
    private final CheckForSnapshotRepoPort checkForSnapshotRepoPort;
    private final StoreScoreboardSnapshotRepoPort storeScoreboardSnapshotRepoPort;

    @Override
    public void createSnapshot() {
        if (!checkForSnapshotRepoPort.isNewSnapshotRequired()) return;
        Scoreboard scoreboard = buildScoreboardService.buildScoreboard();
        storeScoreboardSnapshotRepoPort.store(scoreboard);
    }
}
