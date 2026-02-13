package ch.no1hardy.ost.presentation.rest.race;

import ch.no1hardy.ost.application.race.port.in.RaceStartUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RaceStartController {
    private final RaceStartUseCase raceStartUseCase;

    @PostMapping(value = "/start", produces = "text/plain")
    public String startRace() {
        log.info("Starting new race...");
        return raceStartUseCase.startRace();
    }
}
