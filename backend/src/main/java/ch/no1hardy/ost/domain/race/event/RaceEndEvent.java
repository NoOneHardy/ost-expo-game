package ch.no1hardy.ost.domain.race.event;

import ch.no1hardy.ost.domain.common.event.BaseEvent;
import ch.no1hardy.ost.domain.race.model.Race;

public record RaceEndEvent(
        Race race
) implements BaseEvent {
}
