package ch.no1hardy.ost.application.scoreboard.port.out;

import ch.no1hardy.ost.domain.scoreboard.model.Scoreboard;

import java.util.Optional;

public interface GetLatestScoreboardSnapshotRepoPort {
    Optional<Scoreboard> getLatest();
}
