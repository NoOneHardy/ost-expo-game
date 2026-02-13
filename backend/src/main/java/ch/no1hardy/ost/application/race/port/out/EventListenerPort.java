package ch.no1hardy.ost.application.race.port.out;

import ch.no1hardy.ost.domain.common.event.BaseEvent;

public interface EventListenerPort<T extends BaseEvent> {
    void update(T event);
}
