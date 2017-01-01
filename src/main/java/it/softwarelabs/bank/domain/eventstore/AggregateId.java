package it.softwarelabs.bank.domain.eventstore;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public abstract class AggregateId implements Serializable {

    protected UUID value;

    public AggregateId() {
        this.value = UUID.randomUUID();
    }

    public AggregateId(String value) {
        this.value = UUID.fromString(value);
    }

    public AggregateId(UUID uuid) {
        this.value = uuid;
    }

    public UUID value() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AggregateId)) return false;
        AggregateId that = (AggregateId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
