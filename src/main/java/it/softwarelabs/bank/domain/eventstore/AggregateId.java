package it.softwarelabs.bank.domain.eventstore;

import java.io.Serializable;
import java.util.UUID;

public abstract class AggregateId implements Serializable {

    protected UUID value;

    public AggregateId() {
        this.value = UUID.randomUUID();
    }

    public AggregateId(String value) {
        this.value = UUID.fromString(value);
    }

    public UUID value() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
