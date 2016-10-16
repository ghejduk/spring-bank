package it.softwarelabs.bank.domain.eventstore;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public final class InMemoryEventStoreTest {

    @Test
    public void itIsAEventStore() throws Exception {
        assertThat(new InMemoryEventStore(), is(instanceOf(EventStore.class)));
    }
}
