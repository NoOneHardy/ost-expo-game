package ch.no1hardy.ost.infrastructure.event.listener.race;

import ch.no1hardy.ost.application.race.port.out.EventListenerPort;
import ch.no1hardy.ost.domain.race.event.RaceEndEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersistScoreRaceEndEventEventListener implements EventListenerPort<RaceEndEvent> {

    @Override
    public void update(RaceEndEvent event) {
        // TODO: implement persistence
    }
}
