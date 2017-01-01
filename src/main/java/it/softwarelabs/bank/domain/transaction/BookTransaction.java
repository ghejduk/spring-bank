package it.softwarelabs.bank.domain.transaction;

import it.softwarelabs.bank.domain.account.Account;
import it.softwarelabs.bank.domain.account.AccountException;
import it.softwarelabs.bank.domain.eventstore.EventStore;
import it.softwarelabs.bank.domain.eventstore.exception.EventStoreException;

public final class BookTransaction {

    private final EventStore eventStore;

    public BookTransaction(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public void book(Transaction transaction) throws BookingFailed {
        try {
            Account accountFrom = new Account(eventStore.loadEventStreamFor(transaction.from()));
            Account accountTo = new Account(eventStore.loadEventStreamFor(transaction.to()));

            accountFrom.bookTransaction(transaction);
            accountTo.bookTransaction(transaction);
            transaction.complete();

            eventStore.appendToEventStream(accountFrom);
            eventStore.appendToEventStream(accountTo);
            eventStore.appendToEventStream(transaction);
        } catch (EventStoreException | AccountException | TransactionAlreadyCompleted e) {
            throw new BookingFailed("Could not complete booking.", e);
        }
    }
}
