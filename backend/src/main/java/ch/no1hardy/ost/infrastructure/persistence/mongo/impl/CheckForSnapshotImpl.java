package ch.no1hardy.ost.infrastructure.persistence.mongo.impl;

import ch.no1hardy.ost.application.scoreboard.port.out.CheckForSnapshotRepoPort;
import ch.no1hardy.ost.application.scoreboard.port.out.GetLatestScoreboardSnapshotRepoPort;
import ch.no1hardy.ost.domain.scoreboard.model.Scoreboard;
import ch.no1hardy.ost.infrastructure.persistence.kurrent.repository.ScoreReceivedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CheckForSnapshotImpl implements CheckForSnapshotRepoPort {
    private final GetLatestScoreboardSnapshotRepoPort getLatestScoreboardSnapshotRepoPort;
    private final ScoreReceivedRepository repository;

    @Override
    public boolean isNewSnapshotRequired() {
        Optional<Scoreboard> latest = getLatestScoreboardSnapshotRepoPort.getLatest();
        if (latest.isEmpty()) return true;
        UUID id = latest.map(Scoreboard::latestId).orElseThrow();
        return !repository.isEventInLast(id, 20);
    }
}