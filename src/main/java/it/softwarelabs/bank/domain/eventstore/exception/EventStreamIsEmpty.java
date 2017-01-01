package it.softwarelabs.bank.domain.eventstore.exception;

import it.softwarelabs.bank.domain.eventstore.AggregateId;

public final class EventStreamIsEmpty extends EventStoreException {

    public EventStreamIsEmpty(AggregateId aggregateId) {
        super("Cannot load event stream for aggregate id '" + aggregateId + "' because it is empty.");
    }
}
