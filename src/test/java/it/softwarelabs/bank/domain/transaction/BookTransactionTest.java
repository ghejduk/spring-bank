package it.softwarelabs.bank.domain.transaction;

import it.softwarelabs.bank.domain.account.Account;
import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.account.Money;
import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.eventstore.EventStore;
import it.softwarelabs.bank.domain.eventstore.EventStream;
import it.softwarelabs.bank.domain.user.Email;
import it.softwarelabs.bank.domain.user.User;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class BookTransactionTest {

    @Test
    public void itAppliesTransactionToTheAccounts() throws Exception {
        Account fromAccount = Account.open(
            new AccountId(),
            new Number("QWERTY"),
            User.register(new Email("user1@example.com")),
            new Money(500.0)
        );

        Account toAccount = Account.open(
            new AccountId(),
            new Number("123456"),
            User.register(new Email("user2@example.com")),
            new Money(500.0)
        );

        EventStore eventStore = mock(EventStore.class);
        when(eventStore.loadEventStreamFor(fromAccount.id())).thenReturn(new EventStream(fromAccount.events(), 0));
        when(eventStore.loadEventStreamFor(toAccount.id())).thenReturn(new EventStream(toAccount.events(), 0));

        BookTransaction bookTransaction = new BookTransaction(eventStore);
        Transaction transaction = Transaction.create(fromAccount.id(), toAccount.id(), new Money(100.0));
        bookTransaction.book(transaction);

        assertTrue(transaction.isCompleted());
    }
}
