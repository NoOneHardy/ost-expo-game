package ch.no1hardy.ost.application.race.port.out;

import ch.no1hardy.ost.application.race.dto.in.RaceEndDto;
import ch.no1hardy.ost.domain.race.model.Race;

import java.time.LocalDateTime;

public interface RaceEndRepoPort {
    Race endRace(RaceEndDto raceEndDto, LocalDateTime endDate);
}
