package it.softwarelabs.bank.domain.account.event;

import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.eventstore.Event;

public final class AccountWasClosed extends Event {

    private final AccountId accountId;

    public AccountWasClosed(AccountId accountId) {
        this.accountId = accountId;
    }

    public AccountId accountId() {
        return accountId;
    }
}
