package it.softwarelabs.bank.domain.eventbus;

import it.softwarelabs.bank.domain.eventstore.Event;

import java.util.Set;

public interface EventBus {

    void publish(Event event);

    void publish(Set<Event> events);
}
