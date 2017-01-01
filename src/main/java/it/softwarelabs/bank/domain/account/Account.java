package it.softwarelabs.bank.domain.account;

import it.softwarelabs.bank.domain.account.event.AccountWasClosed;
import it.softwarelabs.bank.domain.account.event.AccountWasOpened;
import it.softwarelabs.bank.domain.account.event.AccountWasReopened;
import it.softwarelabs.bank.domain.account.event.BalanceChanged;
import it.softwarelabs.bank.domain.eventstore.Event;
import it.softwarelabs.bank.domain.eventstore.EventStoreAggregate;
import it.softwarelabs.bank.domain.eventstore.EventStream;
import it.softwarelabs.bank.domain.transaction.Transaction;
import it.softwarelabs.bank.domain.user.User;
import it.softwarelabs.bank.domain.user.UserId;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class Account extends EventStoreAggregate {

    private AccountId id;
    private Number number;
    private UserId ownerId;
    private Balance balance;
    private AccountStatus status = AccountStatus.OPENED;

    public Account(EventStream eventStream) {
        version = eventStream.version();
        eventStream.events().forEach(this::applyEvent);
    }

    public static Account open(AccountId id, Number number, User owner, Money deposit) {
        return new Account(id, number, owner, deposit);
    }

    private Account(AccountId id, Number number, User owner, Money deposit) {
        bumpVersion();
        final AccountWasOpened accountWasOpened = new AccountWasOpened(id, number, owner.id(), deposit, version);
        apply(accountWasOpened);
        this.events.add(accountWasOpened);
    }

    private void applyEvent(Event event) {
        try {
            final Method apply = this.getClass().getDeclaredMethod("apply", event.getClass());
            apply.invoke(this, event);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void apply(AccountWasOpened accountWasOpened) {
        id = accountWasOpened.accountId();
        number = accountWasOpened.number();
        ownerId = accountWasOpened.owner();
        balance = new Balance(accountWasOpened.deposit().toDouble());
    }

    public void bookTransaction(Transaction transaction) throws AccountException {
        if (!transaction.from().equals(id) && !transaction.to().equals(id)) {
            String format = String.format("Account %s is not a part in this transaction %s.", number.toString(), transaction.id().toString());
            throw new CannotTransferFunds(format);
        }

        if (transaction.from().equals(id) && status == AccountStatus.CLOSED) {
            throw new CannotTransferFunds("Cannot transfer funds, because account " + transaction.from() + " is closed.");
        }

        Balance newBalance;

        if (transaction.from().equals(id)) {
            if (status == AccountStatus.CLOSED) {
                throw new CannotTransferFunds("Cannot transfer funds, because account " + transaction.from() + " is closed.");
            }

            newBalance = new Balance(balance.toDouble() - transaction.amount().toDouble());
        } else {
            newBalance = new Balance(balance.toDouble() + transaction.amount().toDouble());
        }

        bumpVersion();
        BalanceChanged balanceChanged = new BalanceChanged(id, newBalance, version);
        apply(balanceChanged);
        events.add(balanceChanged);
    }

    private void apply(BalanceChanged balanceChanged) {
        balance = balanceChanged.balance();
    }

    public void close() throws AccountCannotBeClosed {
        if (status == AccountStatus.CLOSED) {
            throw new AccountCannotBeClosed("Account '" + id + "' is already closed.");
        }

        bumpVersion();
        final AccountWasClosed accountWasClosed = new AccountWasClosed(id, version);
        apply(accountWasClosed);
        events.add(accountWasClosed);
    }

    private void apply(AccountWasClosed accountWasClosed) throws AccountCannotBeClosed {
        this.status = AccountStatus.CLOSED;
    }

    public void reopen() throws AccountCannotBeReopened {
        if (status == AccountStatus.OPENED) {
            throw new AccountCannotBeReopened("Account '" + id + "' is already opened.");
        }

        bumpVersion();
        final AccountWasReopened accountWasReopened = new AccountWasReopened(id, version);
        apply(accountWasReopened);
        events.add(accountWasReopened);
    }

    private void apply(AccountWasReopened accountWasReopened) throws AccountCannotBeReopened {
        this.status = AccountStatus.OPENED;
    }

    public Number getNumber() {
        return number;
    }

    public Balance getBalance() {
        return balance;
    }

    public UserId getOwnerId() {
        return ownerId;
    }

    public AccountId id() {
        return id;
    }

    public Number number() {
        return number;
    }

    public UserId owner() {
        return ownerId;
    }

    public Balance balance() {
        return balance;
    }

    public AccountStatus status() {
        return status;
    }
}
