package it.softwarelabs.bank.application.domain.transaction;

import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.transaction.TransactionId;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public final class JdbcTransactionViewRepository implements TransactionViewRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTransactionViewRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void add(TransactionView transactionView) {
        final MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", transactionView.getId());
        paramSource.addValue("date", new Timestamp(transactionView.getCreatedAt().getMillis()));
        paramSource.addValue("from", transactionView.getFrom());
        paramSource.addValue("to", transactionView.getTo());
        paramSource.addValue("amount", transactionView.getAmount());
        paramSource.addValue("status", transactionView.getStatus());

        jdbcTemplate.update(
            "INSERT INTO transaction (id, \"date\", \"from\", \"to\", amount, status) " +
                "VALUES (:id, :date, :from, :to, :amount, :status)",
            paramSource
        );
    }

    @Override
    public void updateStatus(TransactionId id, int status) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id.value());
        paramSource.addValue("status", status);

        jdbcTemplate.update("UPDATE transaction SET status = :status WHERE id = :id", paramSource);
    }

    @Override
    public List<TransactionView> forAccountId(AccountId id) {
        return jdbcTemplate.query(
            "SELECT * FROM transaction WHERE \"from\" = :id OR \"to\" = :id",
            new MapSqlParameterSource("id", id.value()),
            (rs, rowNum) -> new TransactionView(
                UUID.fromString(rs.getString("id")),
                rs.getDouble("amount"),
                new DateTime(rs.getTimestamp("date")),
                UUID.fromString(rs.getString("from")),
                UUID.fromString(rs.getString("to")),
                rs.getInt("status")
            )
        );
    }
}
