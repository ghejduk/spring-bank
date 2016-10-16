package it.softwarelabs.bank.domain.account.event;

import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.account.Money;
import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.eventstore.Event;

public final class FundsWereTransferred extends Event {

    private final AccountId accountId;
    private final Number from;
    private final Number to;
    private final Money amount;

    public FundsWereTransferred(AccountId accountId, Number from, Number to, Money amount) {
        this.accountId = accountId;
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public AccountId accountId() {
        return accountId;
    }

    public Number from() {
        return from;
    }

    public Number to() {
        return to;
    }

    public Money amount() {
        return amount;
    }
}
