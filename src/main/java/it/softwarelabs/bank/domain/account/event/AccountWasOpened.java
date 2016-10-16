package it.softwarelabs.bank.domain.account.event;

import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.account.Money;
import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.eventstore.Event;
import it.softwarelabs.bank.domain.user.User;

import java.util.Objects;

public final class AccountWasOpened extends Event {

    private final AccountId accountId;
    private final Number number;
    private final User owner;
    private final Money deposit;

    public AccountWasOpened(AccountId accountId, Number number, User owner, Money deposit) {
        this.accountId = accountId;
        this.number = number;
        this.owner = owner;
        this.deposit = deposit;
    }

    public AccountId accountId() {
        return accountId;
    }

    public Number number() {
        return number;
    }

    public User owner() {
        return owner;
    }

    public Money deposit() {
        return deposit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountWasOpened)) return false;
        AccountWasOpened that = (AccountWasOpened) o;
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(number, that.number) &&
                Objects.equals(owner, that.owner) &&
                Objects.equals(deposit, that.deposit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, number, owner, deposit);
    }
}
