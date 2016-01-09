package it.softwarelabs.bank.domain.user;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class UserIdTest {

    @Test
    public void isImmutable() {
        String id = UUID.randomUUID().toString();
        UserId userId = new UserId(id);
        assertSame(id, userId.toString());
    }

    @Test
    public void generatesUUIDWhenConstructorIsEmpty() {
        UserId userId = new UserId();

        assertNotNull(userId.toString());
    }
}