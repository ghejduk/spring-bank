package it.softwarelabs.bank.domain.eventstore;

import java.io.Serializable;
import java.util.UUID;

public abstract class AggregateId implements Serializable {

    protected String value;

    public AggregateId() {
        this.value = UUID.randomUUID().toString();
    }

    public AggregateId(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
