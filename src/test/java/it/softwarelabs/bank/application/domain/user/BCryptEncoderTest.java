package it.softwarelabs.bank.application.domain.user;

import it.softwarelabs.bank.domain.user.PasswordHash;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;

public class BCryptEncoderTest {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void setUp() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder(13);
    }

    @Test
    public void testEncode() {
        BCryptEncoder bCryptEncoder = new BCryptEncoder(bCryptPasswordEncoder);
        assertNotNull(bCryptEncoder.encode("String"));
    }

    @Test
    public void testPasswordIsValid() {
        BCryptEncoder bCryptEncoder = new BCryptEncoder(bCryptPasswordEncoder);
        PasswordHash passwordHash = bCryptEncoder.encode("admin123");

        assertTrue(bCryptEncoder.isValid("admin123", passwordHash));
    }

    @Test
    public void testPasswordIsInvalid() {
        BCryptEncoder bCryptEncoder = new BCryptEncoder(bCryptPasswordEncoder);
        PasswordHash passwordHash = bCryptEncoder.encode("admin1234");

        assertFalse(bCryptEncoder.isValid("admin123", passwordHash));
    }
}