package it.softwarelabs.bank.domain.user;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FullNameTest {

    @Test
    public void isImmutable() {
        String firstName = "James";
        String lastName = "Bond";

        FullName fullName = new FullName(firstName, lastName);

        assertEquals(firstName + " " + lastName, String.valueOf(fullName));
    }
}
