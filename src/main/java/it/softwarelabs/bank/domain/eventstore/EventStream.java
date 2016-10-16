package it.softwarelabs.bank.domain.eventstore;

import java.util.List;

public final class EventStream {

    private final List<Event> events;
    private final long version;

    public EventStream(List<Event> events, long version) {
        this.events = events;
        this.version = version;
    }

    public List<Event> events() {
        return events;
    }

    public long version() {
        return version;
    }
}
