package it.softwarelabs.bank.domain.account;

import org.junit.Test;

import static org.junit.Assert.*;

public class NumberTest {

    @Test
    public void isImmutable() throws IllegalArgumentException {
        String uniqueNumber = "789QWE";
        Number number = new Number(uniqueNumber);

        assertSame(uniqueNumber, String.valueOf(number));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenNumberContainsCharsOtherThenUppercaseAlphanumericsAndDash() throws IllegalArgumentException {
        String uniqueNumber = "789qwe";
        new Number(uniqueNumber);
    }
}