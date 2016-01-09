package it.softwarelabs.bank.domain.account;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class BalanceTest {

    @Test
    public void convertedToStringReturnsUnchangedValue() {
        Double amount = 1150.58;
        Balance balance = new Balance(amount);

        assertEquals(amount, balance.toDouble());
    }
}