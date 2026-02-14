package ch.no1hardy.ost.application.scoreboard.service;

import ch.no1hardy.ost.application.scoreboard.port.in.BuildScoreboardUseCase;
import ch.no1hardy.ost.application.scoreboard.port.out.GetLatestScoreboardSnapshotRepoPort;
import ch.no1hardy.ost.application.scoreboard.port.out.GetNewScoresRepoPort;
import ch.no1hardy.ost.domain.scoreboard.model.Score;
import ch.no1hardy.ost.domain.scoreboard.model.Scoreboard;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class BuildScoreboardService implements BuildScoreboardUseCase {
    private final GetLatestScoreboardSnapshotRepoPort getLatestScoreboardSnapshotRepoPort;
    private final GetNewScoresRepoPort getNewScoresRepoPort;

    @Override
    public Scoreboard buildScoreboard() {
        List<Score> scoreEvents = getNewScoresRepoPort.getNewScores();
        Optional<Scoreboard> latest = getLatestScoreboardSnapshotRepoPort.getLatest();

        return latest.orElseGet(() -> new Scoreboard(UUID.randomUUID(), null, List.of()))
                .addScores(scoreEvents)
                .sortScores();
    }
}
