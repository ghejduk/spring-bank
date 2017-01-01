package it.softwarelabs.bank.domain.account.event;

import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.account.Balance;
import it.softwarelabs.bank.domain.eventstore.Event;

public final class BalanceChanged extends Event {

    private static final long serialVersionUID = 1L;
    private final AccountId accountId;
    private final Balance balance;

    public BalanceChanged(AccountId accountId, Balance balance, long version) {
        this.accountId = accountId;
        this.balance = balance;
        this.version = version;
    }

    public AccountId accountId() {
        return accountId;
    }

    public Balance balance() {
        return balance;
    }
}
