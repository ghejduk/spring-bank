package it.softwarelabs.bank.domain.eventstore;

import java.util.List;

public interface EventStore {

    EventStream loadEventStreamFor(AggregateId id);

    void appendToEventStream(AggregateId id, long expectedVersion, List<Event> events);
}
