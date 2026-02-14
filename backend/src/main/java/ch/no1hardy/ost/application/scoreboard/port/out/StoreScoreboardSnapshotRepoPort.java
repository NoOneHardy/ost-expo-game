package ch.no1hardy.ost.application.scoreboard.port.out;

import ch.no1hardy.ost.domain.scoreboard.model.Scoreboard;

public interface StoreScoreboardSnapshotRepoPort {
    void store(Scoreboard board);
}
