package it.softwarelabs.bank.domain.transaction;

import it.softwarelabs.bank.domain.account.Money;
import it.softwarelabs.bank.domain.account.Number;
import org.junit.Test;

public class TransactionTest {

    @Test
    public void canCreateNewTransaction() throws CannotCompleteTransaction {
        Number from = new Number("123ASD");
        Number to = new Number("ASD123");
        Money amount = new Money(50.0);

        Transaction.create(from, to, amount);
    }

    @Test(expected = CannotCompleteTransaction.class)
    public void throwsExceptionWhenTryingToMakeTransferToTheSameAccount() throws CannotCompleteTransaction {
        Number accountNumber = new Number("123ASD");
        Money amount = new Money(50.0);

        Transaction.create(accountNumber, accountNumber, amount);
    }
}