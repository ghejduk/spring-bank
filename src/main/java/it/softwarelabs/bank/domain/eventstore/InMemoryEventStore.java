package it.softwarelabs.bank.domain.eventstore;

import java.util.List;

public final class InMemoryEventStore implements EventStore {

    @Override
    public EventStream loadEventStreamFor(AggregateId id) {
        return null;
    }

    @Override
    public void appendToEventStream(AggregateId id, long expectedVersion, List<Event> events) {

    }
}
