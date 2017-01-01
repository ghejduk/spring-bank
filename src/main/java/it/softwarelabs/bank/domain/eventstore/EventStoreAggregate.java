package it.softwarelabs.bank.domain.eventstore;

import java.util.ArrayList;
import java.util.List;

public abstract class EventStoreAggregate implements Aggregate {

    protected List<Event> events = new ArrayList<>();
    protected long version = 0;

    @Override
    public List<Event> events() {
        return new ArrayList<>(events);
    }

    @Override
    public void clearEvents() {
        events.clear();
    }

    @Override
    public long version() {
        return version;
    }

    protected void bumpVersion() {
        ++this.version;
    }
}
