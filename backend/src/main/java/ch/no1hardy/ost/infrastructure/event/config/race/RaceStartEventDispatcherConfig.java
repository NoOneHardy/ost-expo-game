package ch.no1hardy.ost.infrastructure.event.config.race;

import ch.no1hardy.ost.application.race.event.RaceStartEventDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RaceStartEventDispatcherConfig {
    @Bean
    public RaceStartEventDispatcher raceStartEventDispatcher() {
        return new RaceStartEventDispatcher();
    }
}
