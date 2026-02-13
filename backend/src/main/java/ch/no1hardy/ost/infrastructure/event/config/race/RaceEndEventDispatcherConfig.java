package ch.no1hardy.ost.infrastructure.event.config.race;

import ch.no1hardy.ost.application.race.event.RaceEndEventDispatcher;
import ch.no1hardy.ost.infrastructure.event.listener.race.PersistScoreRaceEndEventEventListener;
import ch.no1hardy.ost.infrastructure.event.listener.race.PublishViewModeRaceEndEventEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RaceEndEventDispatcherConfig {
    @Bean
    public RaceEndEventDispatcher raceEndEventDispatcher(
            PublishViewModeRaceEndEventEventListener publishViewModeRaceEndEventEventListener,
            PersistScoreRaceEndEventEventListener persistScoreListener
    ) {
        RaceEndEventDispatcher dispatcher = new RaceEndEventDispatcher();
        dispatcher.addListener(publishViewModeRaceEndEventEventListener);
        dispatcher.addListener(persistScoreListener);
        return dispatcher;
    }
}
