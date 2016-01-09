package it.softwarelabs.bank.domain.user;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EmailTest {

    @Test
    public void isImmutable() {
        String emailAddress = "james.bond@mi6.co.uk";
        Email email = new Email(emailAddress);

        assertEquals(emailAddress, String.valueOf(email));
    }

//    @Test(expected = IllegalArgumentException.class)
//    public void throwsExceptionWhenEmailAddressHasInvalidFormat() {
//        Email email = new Email("invalid_email@.pl");
//    }
}
