package ch.no1hardy.ost.infrastructure.event.listener.race;

import ch.no1hardy.ost.application.race.port.out.EventListenerPort;
import ch.no1hardy.ost.application.scoreboard.port.in.CreateScoreboardSnapshotUseCase;
import ch.no1hardy.ost.domain.race.event.RaceEndEvent;
import ch.no1hardy.ost.infrastructure.mapper.race.RaceEndEventMapper;
import ch.no1hardy.ost.infrastructure.persistence.kurrent.repository.ScoreReceivedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersistScoreRaceEndEventEventListener implements EventListenerPort<RaceEndEvent> {
    private final CreateScoreboardSnapshotUseCase createScoreboardSnapshotUseCase;
    private final ScoreReceivedRepository repository;
    private final RaceEndEventMapper mapper;

    @Override
    public void update(RaceEndEvent event) {
        saveScore(event);
        createScoreboardSnapshotUseCase.createSnapshot();
    }

    private void saveScore(RaceEndEvent event) {
        repository.save(mapper.toScoreReceived(event));
    }
}
