package ch.no1hardy.ost.application.race.event.race;

import ch.no1hardy.ost.application.common.event.BaseEventDispatcher;
import ch.no1hardy.ost.domain.race.event.RaceEndEvent;
import ch.no1hardy.ost.domain.race.event.RaceStartEvent;
import org.springframework.stereotype.Component;

@Component
public class RaceEndEventDispatcher extends BaseEventDispatcher<RaceEndEvent> {
}
