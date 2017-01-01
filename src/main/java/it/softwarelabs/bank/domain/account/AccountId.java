package it.softwarelabs.bank.domain.account;

import it.softwarelabs.bank.domain.eventstore.AggregateId;

import java.util.UUID;

public final class AccountId extends AggregateId {

    public AccountId() {
    }

    public AccountId(String value) {
        super(value);
    }

    public AccountId(UUID uuid) {
        super(uuid);
    }
}
