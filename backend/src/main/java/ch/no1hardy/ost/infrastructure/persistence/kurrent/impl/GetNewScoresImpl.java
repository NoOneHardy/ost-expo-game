package ch.no1hardy.ost.infrastructure.persistence.kurrent.impl;

import ch.no1hardy.ost.application.scoreboard.port.out.GetLatestScoreboardSnapshotRepoPort;
import ch.no1hardy.ost.application.scoreboard.port.out.GetNewScoresRepoPort;
import ch.no1hardy.ost.application.scoreboard.port.out.GetScoresSinceRepoPort;
import ch.no1hardy.ost.domain.scoreboard.model.Score;
import ch.no1hardy.ost.domain.scoreboard.model.Scoreboard;
import ch.no1hardy.ost.infrastructure.mapper.scoreboard.ScoreEventMapper;
import ch.no1hardy.ost.infrastructure.persistence.kurrent.repository.ScoreReceivedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GetNewScoresImpl implements GetNewScoresRepoPort {
    private final GetLatestScoreboardSnapshotRepoPort getLatestScoreboardSnapshotRepoPort;
    private final GetScoresSinceRepoPort getScoresSinceRepo;
    private final ScoreReceivedRepository repository;
    private final ScoreEventMapper scoreEventMapper;

    @Override
    public List<Score> getNewScores() {
        return getLatestScoreboardSnapshotRepoPort.getLatest().map(Scoreboard::latestId)
                .map(getScoresSinceRepo::getScoresSince)
                .orElse(repository.findAll().stream().map(scoreEventMapper::toDomain).toList());
    }
}
