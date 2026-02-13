package ch.no1hardy.ost.infrastructure.event.listener.race;

import ch.no1hardy.ost.application.race.port.out.EventListenerPort;
import ch.no1hardy.ost.domain.race.event.RaceStartEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RaceStartEventListener implements EventListenerPort<RaceStartEvent> {
    @Override
    public void update(RaceStartEvent event) {
        // TODO: update websocket to display the race view in frontend
    }
}
