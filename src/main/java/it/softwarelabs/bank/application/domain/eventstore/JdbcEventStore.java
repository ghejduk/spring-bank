package it.softwarelabs.bank.application.domain.eventstore;

import it.softwarelabs.bank.domain.eventbus.EventBus;
import it.softwarelabs.bank.domain.eventstore.*;
import it.softwarelabs.bank.domain.eventstore.exception.EventStoreException;
import it.softwarelabs.bank.domain.eventstore.exception.EventStreamIsEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public final class JdbcEventStore implements EventStore {

    private final JdbcTemplate jdbcTemplate;
    private final EventBus eventBus;

    @Autowired
    public JdbcEventStore(DataSource dataSource, EventBus eventBus) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.eventBus = eventBus;
    }

    public EventStream loadEventStreamFor(AggregateId id) throws EventStoreException {
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(
            "SELECT version, event_body FROM event_store WHERE aggregate_id = ? ORDER BY version ASC",
            id.value()
        );

        if (rows.size() == 0) {
            throw new EventStreamIsEmpty(id);
        }

        final ArrayList<Event> events = new ArrayList<>();
        long version = 0;

        for (Map<String, Object> row : rows) {
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[]) row.get("event_body"));
                final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                events.add((Event) objectInputStream.readObject());
                version = (long) row.get("version");
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return new EventStream(events, version);
    }

    public void appendToEventStream(Aggregate aggregate) throws EventStoreException {
        for (Event event : aggregate.events()) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            try {
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(event);
                oos.flush();
                oos.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            jdbcTemplate.update(
                "INSERT INTO event_store (aggregate_id, version, occurred_at, event_body, event_class) VALUES (?, ?, ?, ?, ?)",
                aggregate.id().value(), event.version(), event.emittedAt(), bos.toByteArray(), event.getClass().getName()
            );
        }

        eventBus.publish(new HashSet<>(aggregate.events()));
        aggregate.clearEvents();
    }
}
