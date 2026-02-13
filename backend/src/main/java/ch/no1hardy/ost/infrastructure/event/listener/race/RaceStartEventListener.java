package ch.no1hardy.ost.infrastructure.event.listener.race;

import ch.no1hardy.ost.application.race.port.out.EventListenerPort;
import ch.no1hardy.ost.domain.race.event.RaceStartEvent;
import ch.no1hardy.ost.presentation.rest.common.ViewModePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RaceStartEventListener implements EventListenerPort<RaceStartEvent> {
    private final ViewModePublisher viewModePublisher;

    @Override
    public void update(RaceStartEvent event) {
        viewModePublisher.publishViewMode();
    }
}
