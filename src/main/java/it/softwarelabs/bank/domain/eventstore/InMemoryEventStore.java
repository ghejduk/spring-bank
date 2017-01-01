package it.softwarelabs.bank.domain.eventstore;

import it.softwarelabs.bank.domain.eventstore.exception.EventStoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class InMemoryEventStore implements EventStore {

    private final Map<AggregateId, List<Event>> events = new HashMap<>();

    @Override
    public EventStream loadEventStreamFor(AggregateId id) {
        List<Event> events = this.events.get(id);

        if (events == null) {
            return new EventStream(new ArrayList<>(), 0);
        }

        return new EventStream(events, events.size());
    }

    @Override
    public void appendToEventStream(Aggregate aggregate) throws EventStoreException {
        events.computeIfAbsent(aggregate.id(), aggregateId -> new ArrayList<>());
        events.get(aggregate.id()).addAll(aggregate.events());
        aggregate.clearEvents();
    }
}
