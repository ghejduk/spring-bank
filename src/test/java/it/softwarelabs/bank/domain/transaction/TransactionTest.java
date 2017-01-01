package it.softwarelabs.bank.domain.transaction;

import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.account.Money;
import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.eventstore.Event;
import it.softwarelabs.bank.domain.eventstore.EventStream;
import it.softwarelabs.bank.domain.transaction.event.TransactionWasCompleted;
import it.softwarelabs.bank.domain.transaction.event.TransactionWasCreated;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class TransactionTest {

    @Test
    public void canCreateNewTransaction() throws CannotCompleteTransaction {
        Transaction.create(new AccountId(), new AccountId(), new Money(50.0));
    }

    @Test(expected = CannotCompleteTransaction.class)
    public void throwsExceptionWhenTryingToMakeTransferToTheSameAccount() throws CannotCompleteTransaction {
        AccountId accountId = new AccountId();
        Transaction.create(accountId, accountId, new Money(50.0));
    }

    @Test
    public void producesTransactionWasCreatedEvent() throws Exception {
        final AccountId from = new AccountId();
        final AccountId to = new AccountId();
        final Money amount = new Money(50.0);

        final Transaction transaction = Transaction.create(from, to, amount);
        assertThat(transaction.events(), hasItem(new TransactionWasCreated(
            transaction.id(),
            transaction.createdAt(),
            from,
            to,
            amount,
            TransactionStatus.PENDING
        )));
    }

    @Test
    public void producesTransactionWasCompletedEvent() throws Exception {
        final Transaction transaction = Transaction.create(new AccountId(), new AccountId(), new Money(50.0));
        transaction.complete();
        assertThat(transaction.events(), hasItem(new TransactionWasCompleted(transaction.id())));
    }

    @Test
    public void changesTransactionStatusToCompleted() throws Exception {
        final Transaction transaction = Transaction.create(new AccountId(), new AccountId(), new Money(50.0));
        transaction.complete();
        assertThat(transaction.status(), is(equalTo(TransactionStatus.COMPLETED)));
    }

    @Test
    public void itCanBeReplayedFromEventStream() throws Exception {
        final TransactionId id = new TransactionId();
        final DateTime createdAt = new DateTime();
        final AccountId from = new AccountId();
        final AccountId to = new AccountId();
        final Money amount = new Money(50.0);

        final ArrayList<Event> events = new ArrayList<>();
        events.add(new TransactionWasCreated(
            id,
            createdAt,
            from,
            to,
            amount,
            TransactionStatus.PENDING
        ));
        final Transaction transaction = new Transaction(new EventStream(events, 1));

        assertThat(transaction.id(), is(equalTo(id)));
        assertThat(transaction.createdAt(), is(equalTo(createdAt)));
        assertThat(transaction.from(), is(equalTo(from)));
        assertThat(transaction.to(), is(equalTo(to)));
        assertThat(transaction.amount(), is(equalTo(amount)));
    }
}
