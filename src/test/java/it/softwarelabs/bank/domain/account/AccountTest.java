package it.softwarelabs.bank.domain.account;

import it.softwarelabs.bank.domain.transaction.Transaction;
import it.softwarelabs.bank.domain.user.User;
import it.softwarelabs.bank.domain.user.UserFactory;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.lessThan;
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

    @Test
    public void afterTransferMyBalanceIsReduced() throws AccountException {
        Number recipient = new Number("QWE123");
        Money amount = new Money(25.0);
        Double balanceBeforeTransfer = account.getBalance().toDouble();

        account.transferFunds(amount, recipient);

        Double balanceAfterTransfer = account.getBalance().toDouble();

        assertEquals(75.0, balanceAfterTransfer);
        assertThat(balanceAfterTransfer, is(lessThan(balanceBeforeTransfer)));
    }

    @Test(expected = NoEnoughFounds.class)
    public void transferFoundsWillThrowExceptionWhenAccountDoesNotHaveEnoughMoney()
            throws AccountException {
        Number recipient = new Number("QWE123");
        Money amount = new Money(125.0);

        account.transferFunds(amount, recipient);
    }

    @Test
    public void transferFundsReturnsTransactionWithProperValues() throws AccountException {
        Number recipient = new Number("QWE123");
        Money amount = new Money(50.0);

        Transaction transaction = account.transferFunds(amount, recipient);

        assertSame(account.getNumber(), transaction.getFrom());
        assertSame(recipient, transaction.getTo());
        assertSame(amount, transaction.getAmount());
    }

    @Test(expected = CannotTransferFunds.class)
    public void transferFundsThrowsExceptionWhenTryingToMakeTransactionToItSelf() throws AccountException {
        Number recipient = new Number(account.getNumber().toString());
        Money amount = new Money(50.0);

        account.transferFunds(amount, recipient);
    }
}
