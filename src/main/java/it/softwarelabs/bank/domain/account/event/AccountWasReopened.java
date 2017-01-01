package it.softwarelabs.bank.domain.account.event;

import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.eventstore.Event;

public final class AccountWasReopened extends Event {

    private static final long serialVersionUID = 1L;
    private final AccountId accountId;

    public AccountWasReopened(AccountId accountId, long version) {
        this.accountId = accountId;
        this.version = version;
    }

    public AccountId accountId() {
        return accountId;
    }
}
