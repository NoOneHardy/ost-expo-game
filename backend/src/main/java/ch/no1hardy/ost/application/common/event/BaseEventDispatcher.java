package ch.no1hardy.ost.application.common.event;

import ch.no1hardy.ost.application.race.port.out.EventListenerPort;
import ch.no1hardy.ost.domain.common.event.BaseEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseEventDispatcher<T extends BaseEvent> {
    private final List<EventListenerPort<T>> listeners = new ArrayList<>();

    public void addListener(EventListenerPort<T> listener) {
        listeners.add(listener);
    }

    public void dispatch(T event) {
        listeners.forEach(listener -> listener.update(event));
    }
}
