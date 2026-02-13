package ch.no1hardy.ost.application.race.port.out;

import ch.no1hardy.ost.application.race.dto.in.RaceEndDto;
import ch.no1hardy.ost.domain.race.model.Race;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface RaceEndRepoPort {
    Optional<Race> endRace(RaceEndDto raceEndDto, LocalDateTime endDate, UUID id);
}
