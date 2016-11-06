package it.softwarelabs.bank.application.domain.eventstore;

import it.softwarelabs.bank.domain.eventbus.EventBus;
import it.softwarelabs.bank.domain.eventstore.AggregateId;
import it.softwarelabs.bank.domain.eventstore.Event;
import it.softwarelabs.bank.domain.eventstore.EventStore;
import it.softwarelabs.bank.domain.eventstore.EventStream;
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

    public EventStream loadEventStreamFor(AggregateId id) {
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(
            "SELECT version, event_body FROM event_store WHERE aggregate_id = :uuid ORDER BY version ASC",
            id.value()
        );
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

    public void appendToEventStream(AggregateId id, long expectedVersion, List<Event> events) {
        long nextVersion = expectedVersion;

        for (Event event : events) {
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
                "INSERT INTO event_store (aggregate_id, version, occurred_at, event_body) VALUES (?, ?, ?, ?)",
                id.value(), ++nextVersion, event.emittedAt(), bos.toByteArray()
            );
        }

        eventBus.publish(new HashSet<>(events));
    }
}
