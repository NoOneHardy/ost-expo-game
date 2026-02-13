package ch.no1hardy.ost.application.race.event;

import ch.no1hardy.ost.application.common.event.BaseEventDispatcher;
import ch.no1hardy.ost.domain.race.event.RaceStartEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RaceStartEventDispatcher extends BaseEventDispatcher<RaceStartEvent> {
}
