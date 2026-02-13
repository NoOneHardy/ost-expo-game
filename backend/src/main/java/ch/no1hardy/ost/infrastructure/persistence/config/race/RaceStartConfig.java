package ch.no1hardy.ost.infrastructure.persistence.config.race;

import ch.no1hardy.ost.application.race.event.race.RaceStartEventDispatcher;
import ch.no1hardy.ost.application.race.port.in.RaceStartUseCase;
import ch.no1hardy.ost.application.race.port.out.RaceStartRepoPort;
import ch.no1hardy.ost.application.race.service.RaceStartService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RaceStartConfig {
    @Bean
    public RaceStartUseCase raceStartUseCase(
            RaceStartRepoPort raceStartRepo,
            RaceStartEventDispatcher raceStartEventDispatcher
    ) {
        return new RaceStartService(raceStartRepo, raceStartEventDispatcher);
    }
}
