package it.softwarelabs.bank.domain.transaction;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class TransactionIdTest {

    @Test
    public void isImmutable() {
        String id = UUID.randomUUID().toString();
        TransactionId transactionId = new TransactionId(id);
        assertSame(id, transactionId.toString());
    }

    @Test
    public void generatesUUIDWhenConstructorIsEmpty() {
        TransactionId transactionId = new TransactionId();

        assertNotNull(transactionId.toString());
    }
}