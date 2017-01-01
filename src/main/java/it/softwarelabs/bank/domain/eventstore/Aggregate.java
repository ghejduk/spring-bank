package it.softwarelabs.bank.domain.eventstore;

import java.util.List;

public interface Aggregate {

    AggregateId id();

    List<Event> events();

    void clearEvents();

    long version();
}
