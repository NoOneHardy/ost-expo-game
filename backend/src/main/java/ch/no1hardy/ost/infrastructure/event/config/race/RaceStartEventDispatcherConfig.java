package ch.no1hardy.ost.infrastructure.event.config.race;

import ch.no1hardy.ost.application.race.event.RaceStartEventDispatcher;
import ch.no1hardy.ost.infrastructure.event.listener.race.RaceStartEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RaceStartEventDispatcherConfig {
    @Bean
    public RaceStartEventDispatcher raceStartEventDispatcher(
            RaceStartEventListener listener
    ) {
        RaceStartEventDispatcher dispatcher = new RaceStartEventDispatcher();
        dispatcher.addListener(listener);
        return dispatcher;
    }
}
