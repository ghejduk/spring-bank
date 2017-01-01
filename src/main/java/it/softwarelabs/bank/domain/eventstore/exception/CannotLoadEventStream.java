package it.softwarelabs.bank.domain.eventstore.exception;

import it.softwarelabs.bank.domain.eventstore.AggregateId;

public final class CannotLoadEventStream extends EventStoreException {

    public CannotLoadEventStream(AggregateId aggregateId) {
        super("Cannot load event stream for aggregate with id '" + aggregateId + "'.");
    }

    public CannotLoadEventStream(AggregateId aggregateId, Throwable previous) {
        super("Cannot load event stream for aggregate with id '" + aggregateId + "'.", previous);
    }
}
