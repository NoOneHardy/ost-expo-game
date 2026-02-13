package ch.no1hardy.ost.application.race.port.out;

import java.time.LocalDateTime;
import java.util.UUID;

public interface RaceStartRepoPort {
    UUID saveRaceStart(LocalDateTime start);
}
