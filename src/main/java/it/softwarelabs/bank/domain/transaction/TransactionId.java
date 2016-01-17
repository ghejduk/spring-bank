package it.softwarelabs.bank.domain.transaction;

import java.io.Serializable;
import java.util.UUID;

public class TransactionId implements Serializable {

    private String value;

    public TransactionId() {
        this.value = UUID.randomUUID().toString();
    }

    public TransactionId(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
