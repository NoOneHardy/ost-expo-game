package ch.no1hardy.ost.presentation.rest.race;

import ch.no1hardy.ost.application.race.dto.in.RaceEndDto;
import ch.no1hardy.ost.application.race.dto.out.RaceDto;
import ch.no1hardy.ost.application.race.port.in.RaceEndUseCase;
import ch.no1hardy.ost.application.race.port.in.RaceStartUseCase;
import ch.no1hardy.ost.infrastructure.mapper.race.RaceDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RaceEndController {
    private final RaceEndUseCase raceEndUseCase;
    private final RaceDtoMapper mapper;

    @PostMapping(value = "/end/{id}")
    public Optional<RaceDto> endRace(@PathVariable UUID id, @RequestBody RaceEndDto dto) {
        log.info("Ending race race {}", id);
        return raceEndUseCase.endRace(dto, id).map(mapper::toDto);
    }
}
