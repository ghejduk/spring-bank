package it.softwarelabs.bank.domain.account;

import org.junit.Test;

import static org.junit.Assert.*;

public class MoneyTest {

    @Test
    public void convertedToStringReturnsUnchangedValue() {
        Double amount = 1150.58;
        Money money = new Money(amount);

        assertEquals(amount, money.toDouble());
    }
}