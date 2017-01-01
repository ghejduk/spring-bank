package it.softwarelabs.bank.domain.transaction.event;

import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.account.Money;
import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.eventstore.Event;
import it.softwarelabs.bank.domain.transaction.TransactionId;
import it.softwarelabs.bank.domain.transaction.TransactionStatus;
import org.joda.time.DateTime;

import java.util.Objects;

public final class TransactionWasCreated extends Event {

    private TransactionId id;
    private DateTime createdAt;
    private AccountId from;
    private AccountId to;
    private Money amount;
    private TransactionStatus status;

    public TransactionWasCreated(TransactionId id, DateTime createdAt, AccountId from, AccountId to, Money amount, TransactionStatus status, long version) {
        this.id = id;
        this.createdAt = createdAt;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.status = status;
        this.version = version;
    }

    public TransactionId id() {
        return id;
    }

    public DateTime createdAt() {
        return createdAt;
    }

    public AccountId from() {
        return from;
    }

    public AccountId to() {
        return to;
    }

    public Money amount() {
        return amount;
    }

    public TransactionStatus status() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionWasCreated)) return false;
        TransactionWasCreated that = (TransactionWasCreated) o;

        return Objects.equals(id, that.id) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(from, that.from) &&
            Objects.equals(to, that.to) &&
            Objects.equals(amount, that.amount) &&
            status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, from, to, amount, status);
    }
}
