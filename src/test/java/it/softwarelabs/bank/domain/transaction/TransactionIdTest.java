package it.softwarelabs.bank.domain.transaction;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionIdTest {

    @Test
    public void returnsIdPassedInTheConstructor() throws IllegalArgumentException {
        TransactionId transactionId = new TransactionId(456);
        assertEquals(456, transactionId.toInt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenIfPassedIdIsLessThanZero() throws IllegalArgumentException {
        new TransactionId(0);
    }
}