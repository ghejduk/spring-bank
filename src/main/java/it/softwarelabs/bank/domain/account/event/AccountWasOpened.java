package it.softwarelabs.bank.domain.account.event;

import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.account.Money;
import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.eventstore.Event;
import it.softwarelabs.bank.domain.user.User;
import it.softwarelabs.bank.domain.user.UserId;

import java.util.Objects;

public final class AccountWasOpened extends Event {

    private static final long serialVersionUID = 1L;
    private final AccountId accountId;
    private final Number number;
    private final UserId owner;
    private final Money deposit;

    public AccountWasOpened(AccountId accountId, Number number, UserId owner, Money deposit, long version) {
        this.accountId = accountId;
        this.number = number;
        this.owner = owner;
        this.deposit = deposit;
        this.version = version;
    }

    public AccountId accountId() {
        return accountId;
    }

    public Number number() {
        return number;
    }

    public UserId owner() {
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
