package ch.no1hardy.ost.infrastructure.persistence.jpa.port;

import ch.no1hardy.ost.application.race.port.out.OpenRaceRepoPort;
import ch.no1hardy.ost.infrastructure.persistence.jpa.repository.RaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OpenRaceRepoImpl implements OpenRaceRepoPort {
    private final RaceRepository raceRepository;

    @Override
    public boolean hasOpenRaces() {
        return !raceRepository.findWhereEndTimeIsNull().isEmpty();
    }
}
