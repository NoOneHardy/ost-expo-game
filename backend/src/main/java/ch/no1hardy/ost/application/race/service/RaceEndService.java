package ch.no1hardy.ost.application.race.service;

import ch.no1hardy.ost.application.race.dto.in.RaceEndDto;
import ch.no1hardy.ost.application.race.event.RaceEndEventDispatcher;
import ch.no1hardy.ost.application.race.port.in.RaceEndUseCase;
import ch.no1hardy.ost.application.race.port.out.RaceEndRepoPort;
import ch.no1hardy.ost.domain.race.event.RaceEndEvent;
import ch.no1hardy.ost.domain.race.model.Race;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class RaceEndService implements RaceEndUseCase {
    private final RaceEndRepoPort repository;
    private final RaceEndEventDispatcher dispatcher;

    @Override
    public Optional<Race> endRace(RaceEndDto raceEndDto, UUID id) {
        LocalDateTime endDate = LocalDateTime.now();
        Optional<Race> race = repository.endRace(raceEndDto, endDate, id);
        race.ifPresent(r -> dispatcher.dispatch(new RaceEndEvent(r)));
        return race;
    }
}