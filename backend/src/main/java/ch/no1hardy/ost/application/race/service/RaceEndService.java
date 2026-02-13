package ch.no1hardy.ost.application.race.service;

import ch.no1hardy.ost.application.race.dto.in.RaceEndDto;
import ch.no1hardy.ost.application.race.event.race.RaceEndEventDispatcher;
import ch.no1hardy.ost.application.race.event.race.RaceStartEventDispatcher;
import ch.no1hardy.ost.application.race.port.in.RaceEndUseCase;
import ch.no1hardy.ost.application.race.port.in.RaceStartUseCase;
import ch.no1hardy.ost.application.race.port.out.RaceEndRepoPort;
import ch.no1hardy.ost.application.race.port.out.RaceStartRepoPort;
import ch.no1hardy.ost.domain.race.event.RaceEndEvent;
import ch.no1hardy.ost.domain.race.event.RaceStartEvent;
import ch.no1hardy.ost.domain.race.model.Race;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class RaceEndService implements RaceEndUseCase {
    private final RaceEndRepoPort repository;
    private final RaceEndEventDispatcher dispatcher;

    @Override
    public void endRace(RaceEndDto raceEndDto) {
        LocalDateTime endDate = LocalDateTime.now();
        Race race = repository.endRace(raceEndDto, endDate);
        dispatcher.dispatch(new RaceEndEvent(race));
    }
}