package it.softwarelabs.bank.domain.eventstore;

import it.softwarelabs.bank.domain.eventstore.exception.EventStoreException;

public interface EventStore {

    EventStream loadEventStreamFor(AggregateId id) throws EventStoreException;

    void appendToEventStream(Aggregate aggregate) throws EventStoreException;
}
