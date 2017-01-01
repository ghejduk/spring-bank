package it.softwarelabs.bank.domain.account;

import it.softwarelabs.bank.domain.account.event.AccountWasOpened;
import it.softwarelabs.bank.domain.eventstore.Event;
import it.softwarelabs.bank.domain.eventstore.EventStream;
import it.softwarelabs.bank.domain.transaction.Transaction;
import it.softwarelabs.bank.domain.user.Email;
import it.softwarelabs.bank.domain.user.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AccountTest {

    @Test
    public void clearsEventListAfterTheyWasRead() throws Exception {
        final AccountId id = new AccountId();
        final Number number = new Number("789ASD");
        final User owner = User.register(new Email("test@example.com"));
        final Money deposit = new Money(5.50);
        final Account account = Account.open(id, number, owner, deposit);

        assertThat(account.events().isEmpty(), is(false));
        account.clearEvents();
        assertThat(account.events(), equalTo(new ArrayList<>()));
    }

    @Test
    public void producesEventAboutOpenedAccount() throws Exception {
        final AccountId id = new AccountId();
        final Number number = new Number("789ASD");
        final User owner = User.register(new Email("test@example.com"));
        final Money deposit = new Money(5.50);
        final Account account = Account.open(id, number, owner, deposit);

        assertThat(account.events(), hasItem(new AccountWasOpened(id, number, owner.id(), deposit)));
    }

    @Test
    public void createsAccountFromEventStream() throws Exception {
        final AccountId id = new AccountId();
        final Number number = new Number("789ASD");
        final User owner = User.register(new Email("test@example.com"));
        final Money deposit = new Money(5.50);

        final List<Event> events = new ArrayList<>();
        events.add(new AccountWasOpened(id, number, owner.id(), deposit));

        final Account account = new Account(new EventStream(events, 1));
    }

    @Test(expected = CannotTransferFunds.class)
    public void throwsExceptionWhenIsNotPartOfTheTransaction() throws Throwable {
        final AccountId id = new AccountId();
        final User owner = User.register(new Email("test@example.com"));
        final Money deposit = new Money(5.50);
        final Account account = Account.open(id, new Number("ASD789"), owner, deposit);

        Transaction transaction = Transaction.create(new AccountId(), new AccountId(), new Money(15.0));
        account.bookTransaction(transaction);
    }

    @Test
    public void reducesBalanceInFromAccount() throws Throwable {
        final AccountId id = new AccountId();
        final Number number = new Number("789ASD");
        final User owner = User.register(new Email("test@example.com"));
        final Money deposit = new Money(100.0);
        final Account account = Account.open(id, number, owner, deposit);

        Transaction transaction = Transaction.create(id, new AccountId(), new Money(25.0));
        Double balanceAfterTransaction = 75.0;

        account.bookTransaction(transaction);

        assertEquals(account.getBalance().toDouble(), balanceAfterTransaction);
    }

    @Test
    public void addsBalanceIfToAccount() throws Throwable {
        final AccountId id = new AccountId();
        final Number number = new Number("789ASD");
        final User owner = User.register(new Email("test@example.com"));
        final Money deposit = new Money(100.0);
        final Account account = Account.open(id, number, owner, deposit);

        Transaction transaction = Transaction.create(new AccountId(), id, new Money(25.0));
        Double balanceAfterTransaction = 125.0;

        account.bookTransaction(transaction);

        assertEquals(account.getBalance().toDouble(), balanceAfterTransaction);
    }
}
