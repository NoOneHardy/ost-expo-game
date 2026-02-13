package ch.no1hardy.ost.infrastructure.event.config.race;

import ch.no1hardy.ost.application.race.event.RaceEndEventDispatcher;
import ch.no1hardy.ost.infrastructure.event.listener.race.RaceEndEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RaceEndEventDispatcherConfig {
    @Bean
    public RaceEndEventDispatcher raceEndEventDispatcher(
            RaceEndEventListener listener
    ) {
        RaceEndEventDispatcher dispatcher = new RaceEndEventDispatcher();
        dispatcher.addListener(listener);
        return dispatcher;
    }
}
