package ch.no1hardy.ost.infrastructure.config.race;

import ch.no1hardy.ost.application.race.event.RaceEndEventDispatcher;
import ch.no1hardy.ost.application.race.port.in.RaceEndUseCase;
import ch.no1hardy.ost.application.race.port.out.RaceEndRepoPort;
import ch.no1hardy.ost.application.race.service.RaceEndService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RaceEndConfig {
    @Bean
    public RaceEndUseCase raceEndUseCase(
            RaceEndRepoPort repository,
            RaceEndEventDispatcher dispatcher
    ) {
        return new RaceEndService(repository, dispatcher);
    }
}
