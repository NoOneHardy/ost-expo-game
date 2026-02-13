package ch.no1hardy.ost.application.race.service;

import ch.no1hardy.ost.application.race.event.RaceStartEventDispatcher;
import ch.no1hardy.ost.application.race.port.in.RaceStartUseCase;
import ch.no1hardy.ost.application.race.port.out.RaceStartRepoPort;
import ch.no1hardy.ost.domain.race.event.RaceStartEvent;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class RaceStartService implements RaceStartUseCase {
    private final RaceStartRepoPort raceWriteRepo;
    private final RaceStartEventDispatcher dispatcher;

    @Override
    public String startRace() {
        LocalDateTime startDate = LocalDateTime.now();
        UUID id = raceWriteRepo.saveRaceStart(startDate);
        dispatcher.dispatch(new RaceStartEvent());
        return id.toString();
    }
}