package ch.no1hardy.ost.infrastructure.persistence.jpa.port;

import ch.no1hardy.ost.application.race.port.out.RaceStartRepoPort;
import ch.no1hardy.ost.infrastructure.persistence.jpa.entity.RaceEntity;
import ch.no1hardy.ost.infrastructure.persistence.jpa.repository.RaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RaceStartRepoImpl implements RaceStartRepoPort {
    private final RaceRepository repository;

    @Override
    public UUID saveRaceStart(LocalDateTime start) {
        RaceEntity raceEntity = new RaceEntity();
        raceEntity.setStartTime(start);
        repository.save(raceEntity);
        return raceEntity.getId();
    }
}
