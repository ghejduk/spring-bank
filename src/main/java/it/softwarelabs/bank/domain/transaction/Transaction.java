package it.softwarelabs.bank.domain.transaction;

import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.account.Money;
import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.eventstore.Event;
import it.softwarelabs.bank.domain.eventstore.EventStoreAggregate;
import it.softwarelabs.bank.domain.eventstore.EventStream;
import it.softwarelabs.bank.domain.transaction.event.TransactionWasCompleted;
import it.softwarelabs.bank.domain.transaction.event.TransactionWasCreated;
import org.joda.time.DateTime;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class Transaction extends EventStoreAggregate {

    private TransactionId id;
    private DateTime date;
    private AccountId from;
    private AccountId to;
    private Money amount;
    private TransactionStatus status;

    public Transaction(EventStream eventStream) {
        version = eventStream.version();
        eventStream.events().forEach(this::applyEvent);
    }

    private Transaction(AccountId from, AccountId to, Money amount) throws CannotCompleteTransaction {
        if (from.equals(to)) {
            String format = "Transaction must involve two different account numbers. Given number: '%s'.";
            String message = String.format(format, from.toString());
            throw new CannotCompleteTransaction(message);
        }

        bumpVersion();
        TransactionWasCreated transactionWasCreated = new TransactionWasCreated(
            new TransactionId(),
            new DateTime(),
            from,
            to,
            amount,
            TransactionStatus.PENDING,
            version
        );
        apply(transactionWasCreated);
        events.add(transactionWasCreated);
    }

    public static Transaction create(AccountId from, AccountId to, Money amount) throws CannotCompleteTransaction {
        return new Transaction(from, to, amount);
    }

    private void applyEvent(Event event) {
        try {
            final Method apply = this.getClass().getDeclaredMethod("apply", event.getClass());
            apply.invoke(this, event);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void apply(TransactionWasCreated transactionWasCreated) {
        this.id = transactionWasCreated.id();
        this.from = transactionWasCreated.from();
        this.to = transactionWasCreated.to();
        this.amount = transactionWasCreated.amount();
        this.status = transactionWasCreated.status();
        this.date = transactionWasCreated.createdAt();
    }

    public void complete() throws TransactionAlreadyCompleted {
        if (isCompleted()) {
            throw new TransactionAlreadyCompleted("Transaction '" + id + "' is already completed.");
        }

        bumpVersion();
        TransactionWasCompleted transactionWasCompleted = new TransactionWasCompleted(id, version);
        apply(transactionWasCompleted);
        this.events.add(transactionWasCompleted);
    }

    private void apply(TransactionWasCompleted transactionWasCompleted) {
        this.status = TransactionStatus.COMPLETED;
    }

    public boolean isCompleted() {
        return status.equals(TransactionStatus.COMPLETED);
    }

    public DateTime createdAt() {
        return date;
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

    public TransactionId id() {
        return id;
    }

    public TransactionStatus status() {
        return status;
    }
}
