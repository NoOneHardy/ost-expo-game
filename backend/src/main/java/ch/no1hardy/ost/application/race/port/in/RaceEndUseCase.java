package ch.no1hardy.ost.application.race.port.in;

import ch.no1hardy.ost.application.race.dto.in.RaceEndDto;
import ch.no1hardy.ost.domain.race.model.Race;

import java.util.Optional;
import java.util.UUID;

public interface RaceEndUseCase {
    Optional<Race> endRace(RaceEndDto raceEndDto, UUID id);
}
