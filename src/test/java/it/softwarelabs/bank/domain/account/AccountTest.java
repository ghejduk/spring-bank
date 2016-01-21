package it.softwarelabs.bank.domain.account;

import it.softwarelabs.bank.domain.transaction.Transaction;
import it.softwarelabs.bank.domain.user.User;
import it.softwarelabs.bank.domain.user.UserFactory;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

public class AccountTest {

    private Account account;

    @Before
    public void createAccount() {
        Number number = new Number("ADS123");
        UserFactory userFactory = new UserFactory();
        User owner = userFactory.create("John", "Doe", "john@doe.com");
        Money deposit = new Money(100.0);

        account = Account.open(number, owner, deposit);
    }

    @Test
    public void openAccountMethodWillReturnNewInstance() {
        Number number = new Number("ADS123");
        UserFactory userFactory = new UserFactory();
        User owner = userFactory.create("John", "Doe", "john@doe.com");
        Money deposit = new Money(100.0);

        assertThat(Account.open(number, owner, deposit), instanceOf(Account.class));
    }

    @Test
    public void getNumberWillReturnNumberPassedInConstructor() {
        Number number = new Number("ADS123");
        UserFactory userFactory = new UserFactory();
        User owner = userFactory.create("John", "Doe", "john@doe.com");
        Money deposit = new Money(100.0);
        Account account = Account.open(number, owner, deposit);

        assertSame(number, account.getNumber());
    }

    @Test
    public void getOwnerWillReturnUserPassedInConstructor() {
        Number number = new Number("ADS123");
        UserFactory userFactory = new UserFactory();
        User owner = userFactory.create("John", "Doe", "john@doe.com");
        Money deposit = new Money(100.0);
        Account account = Account.open(number, owner, deposit);

        assertSame(owner, account.getOwner());
    }

    @Test
    public void newAccountHasBalanceEqualedToTheDeposit() {
        Number number = new Number("ADS123");
        UserFactory userFactory = new UserFactory();
        User owner = userFactory.create("John", "Doe", "john@doe.com");
        Money deposit = new Money(50.0);
        Account account = Account.open(number, owner, deposit);

        assertEquals(account.getBalance().toDouble(), 50.0);
    }

    @Test(expected = CannotTransferFunds.class)
    public void throwsExceptionWhenIsNotPartOfTheTransaction() throws Throwable {
        Transaction transaction = Transaction.create(new Number("123456"), new Number("789654"), new Money(15.0));
        account.bookTransaction(transaction);
    }

    @Test
    public void reducesBalanceInFromAccount() throws Throwable {
        Transaction transaction = Transaction.create(account.getNumber(), new Number("789654"), new Money(25.0));
        Double balanceAfterTransaction = 75.0;

        account.bookTransaction(transaction);

        assertEquals(account.getBalance().toDouble(), balanceAfterTransaction);
    }

    @Test
    public void addsBalanceIfToAccount() throws Throwable {
        Transaction transaction = Transaction.create(new Number("789654"), account.getNumber(), new Money(25.0));
        Double balanceAfterTransaction = 125.0;

        account.bookTransaction(transaction);

        assertEquals(account.getBalance().toDouble(), balanceAfterTransaction);
    }
}
