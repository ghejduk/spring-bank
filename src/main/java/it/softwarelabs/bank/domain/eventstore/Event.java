package it.softwarelabs.bank.domain.eventstore;

import java.util.Date;

public abstract class Event {

    private final Date emittedAt = new Date();
    protected long version;

    public Date emittedAt() {
        return emittedAt;
    }

    public long version() {
        return version;
    }
}
