package it.softwarelabs.bank.domain.eventstore;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventStream)) return false;
        EventStream that = (EventStream) o;
        return version == that.version &&
            Objects.equals(events, that.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(events, version);
    }
}
