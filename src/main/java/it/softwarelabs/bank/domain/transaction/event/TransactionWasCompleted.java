package it.softwarelabs.bank.domain.transaction.event;

import it.softwarelabs.bank.domain.eventstore.Event;
import it.softwarelabs.bank.domain.transaction.TransactionId;

import java.util.Objects;

public final class TransactionWasCompleted extends Event {

    private final TransactionId transactionId;

    public TransactionWasCompleted(TransactionId transactionId, long version) {
        this.transactionId = transactionId;
        this.version = version;
    }

    public TransactionId transactionId() {
        return transactionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionWasCompleted)) return false;
        TransactionWasCompleted that = (TransactionWasCompleted) o;
        return Objects.equals(transactionId, that.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }
}
