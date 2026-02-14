package ch.no1hardy.ost.infrastructure.persistence.mongo.impl;

import ch.no1hardy.ost.application.scoreboard.port.out.GetLatestScoreboardSnapshotRepoPort;
import ch.no1hardy.ost.domain.scoreboard.model.Scoreboard;
import ch.no1hardy.ost.infrastructure.mapper.scoreboard.ScoreboardDocumentMapper;
import ch.no1hardy.ost.infrastructure.persistence.mongo.document.ScoreboardDocument;
import ch.no1hardy.ost.infrastructure.persistence.mongo.repository.ScoreboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GetLatestScoreboardSnapshotImpl implements GetLatestScoreboardSnapshotRepoPort {
    private final ScoreboardRepository scoreboardRepository;
    private final ScoreboardDocumentMapper mapper;

    @Override
    public Optional<Scoreboard> getLatest() {
        List<ScoreboardDocument> documents = scoreboardRepository.findAll();
        if (documents.isEmpty()) return Optional.empty();
        return Optional.of(documents.getLast()).map(mapper::toDomain);
    }
}
