package it.softwarelabs.bank.domain.transaction;

import it.softwarelabs.bank.domain.eventstore.AggregateId;

public final class TransactionId extends AggregateId {

    public TransactionId() {
    }

    public TransactionId(String value) {
        super(value);
    }
}
