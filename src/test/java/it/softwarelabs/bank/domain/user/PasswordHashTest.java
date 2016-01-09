package it.softwarelabs.bank.domain.user;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class PasswordHashTest {

    @Test
    public void toStringReturnsValuePassedInConstructor() {
        String passwordHash = "PasswordHash";
        PasswordHash password = new PasswordHash(passwordHash);

        assertEquals(passwordHash, password.toString());
    }
}