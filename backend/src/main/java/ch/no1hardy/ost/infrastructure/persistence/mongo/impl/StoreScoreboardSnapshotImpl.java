package ch.no1hardy.ost.infrastructure.persistence.mongo.impl;

import ch.no1hardy.ost.application.scoreboard.port.out.StoreScoreboardSnapshotRepoPort;
import ch.no1hardy.ost.domain.scoreboard.model.Scoreboard;
import ch.no1hardy.ost.infrastructure.mapper.scoreboard.ScoreboardDocumentMapper;
import ch.no1hardy.ost.infrastructure.persistence.mongo.document.ScoreboardDocument;
import ch.no1hardy.ost.infrastructure.persistence.mongo.repository.ScoreboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StoreScoreboardSnapshotImpl implements StoreScoreboardSnapshotRepoPort {
    private final ScoreboardRepository scoreboardRepository;
    private final ScoreboardDocumentMapper mapper;

    @Override
    public void store(Scoreboard board) {
        ScoreboardDocument document = mapper.toDocument(board);
        scoreboardRepository.save(document);
    }
}
