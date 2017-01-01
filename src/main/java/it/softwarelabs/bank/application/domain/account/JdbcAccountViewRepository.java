package it.softwarelabs.bank.application.domain.account;

import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.user.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public final class JdbcAccountViewRepository implements AccountViewRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcAccountViewRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void add(UUID id, String number, double balance, UUID ownerId) {
        final MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id);
        paramSource.addValue("number", number);
        paramSource.addValue("balance", balance);
        paramSource.addValue("owner_id", ownerId);

        jdbcTemplate.update(
            "INSERT INTO account (id, number, balance, owner_id) VALUES (:id, :number, :balance, :owner_id)",
            paramSource
        );
    }

    @Override
    public void updateBalance(AccountId accountId, double balance) {
        final MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", accountId.value());
        paramSource.addValue("balance", balance);

        jdbcTemplate.update(
            "UPDATE account SET balance = :balance WHERE id = :id",
            paramSource
        );
    }

    @Override
    public List<AccountView> all() {
        return jdbcTemplate.query(
            "SELECT * FROM account",
            new AccountViewRowMapper()
        );
    }

    @Override
    public List<AccountView> findForOwner(UserId id) {
        return jdbcTemplate.query(
            "SELECT * FROM account WHERE owner_id = :ownerId",
            new MapSqlParameterSource("ownerId", id.value()),
            new AccountViewRowMapper()
        );
    }

    @Override
    public AccountView forNumber(Number number) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM account WHERE number = :number",
            new MapSqlParameterSource("number", number.toString()),
            new AccountViewRowMapper()
        );
    }

    @Override
    public AccountView forAccountId(AccountId accountId) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM account WHERE id = :id",
            new MapSqlParameterSource("id", accountId.value()),
            new AccountViewRowMapper()
        );
    }

    private class AccountViewRowMapper implements RowMapper<AccountView> {

        @Override
        public AccountView mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new AccountView(
                UUID.fromString(rs.getString("id")),
                rs.getString("number"),
                rs.getDouble("balance"),
                UUID.fromString(rs.getString("owner_id"))
            );
        }
    }
}
