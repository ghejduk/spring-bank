package it.softwarelabs.bank.domain.eventstore;

import java.util.List;

public interface Aggregate {

    List<Event> producedEvents();
}
