package ch.no1hardy.ost.infrastructure.persistence.kurrent.impl;

import ch.no1hardy.ost.application.scoreboard.port.out.GetScoresSinceRepoPort;
import ch.no1hardy.ost.domain.scoreboard.model.Score;
import ch.no1hardy.ost.infrastructure.mapper.scoreboard.ScoreEventMapper;
import ch.no1hardy.ost.infrastructure.persistence.kurrent.event.ScoreReceived;
import ch.no1hardy.ost.infrastructure.persistence.kurrent.repository.ScoreReceivedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class GetScoresSinceImpl implements GetScoresSinceRepoPort {
    private final ScoreReceivedRepository repository;
    private final ScoreEventMapper mapper;

    @Override
    public List<Score> getScoresSince(UUID id) {
        List<ScoreReceived> all = repository.findAll();
        return repository.findById(id).map(all::indexOf)
                .map(i -> all.subList(i + 1, all.size()).stream()
                        .map(mapper::toDomain)
                        .toList())
                .orElse(List.of());
    }
}
