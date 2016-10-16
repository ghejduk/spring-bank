package it.softwarelabs.bank.domain.account;

import it.softwarelabs.bank.domain.account.event.AccountWasClosed;
import it.softwarelabs.bank.domain.account.event.AccountWasOpened;
import it.softwarelabs.bank.domain.account.event.AccountWasReopened;
import it.softwarelabs.bank.domain.account.event.FundsWereTransferred;
import it.softwarelabs.bank.domain.eventstore.Aggregate;
import it.softwarelabs.bank.domain.eventstore.Event;
import it.softwarelabs.bank.domain.eventstore.EventStream;
import it.softwarelabs.bank.domain.transaction.Transaction;
import it.softwarelabs.bank.domain.user.User;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class Account implements Aggregate {

    enum AccountStatus {OPENED, CLOSED}

    private AccountId id;
    private Number number;
    private User owner;
    private Balance balance;
    private long version;
    private List<Event> producedEvents = new ArrayList<>();
    private AccountStatus status = AccountStatus.OPENED;

    public Account(EventStream eventStream) {
        version = eventStream.version();
        eventStream.events().forEach(this::applyEvent);
    }

    public static Account open(AccountId id, Number number, User owner, Money deposit) {
        return new Account(id, number, owner, deposit);
    }

    private Account(AccountId id, Number number, User owner, Money deposit) {
        final AccountWasOpened accountWasOpened = new AccountWasOpened(id, number, owner, deposit);
        applyEvent(accountWasOpened);
        this.producedEvents.add(accountWasOpened);
    }

    private void applyEvent(Event event) {
        try {
            final Method apply = this.getClass().getDeclaredMethod("apply", event.getClass());
            apply.invoke(this, event);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Event> producedEvents() {
        final List<Event> events = new ArrayList<>(producedEvents);
        producedEvents.clear();
        return events;
    }

    private void apply(AccountWasOpened accountWasOpened) {
        id = accountWasOpened.accountId();
        number = accountWasOpened.number();
        owner = accountWasOpened.owner();
        balance = new Balance(accountWasOpened.deposit().toDouble());
    }

    public void bookTransaction(Transaction transaction) throws AccountException {
        if (!transaction.getFrom().equals(number) && !transaction.getTo().equals(number)) {
            String format = String.format("Account %s is not a part in this transaction %s.", number.toString(), transaction.getId().toString());
            throw new CannotTransferFunds(format);
        }

        final FundsWereTransferred fundsWereTransferred = new FundsWereTransferred(
                id,
                transaction.getFrom(),
                transaction.getTo(),
                transaction.getAmount()
        );
        apply(fundsWereTransferred);
        producedEvents.add(fundsWereTransferred);
    }

    private void apply(FundsWereTransferred fundsWereTransferred) throws CannotTransferFunds {
        if (fundsWereTransferred.from().equals(number)) {
            if (status == AccountStatus.CLOSED) {
                throw new CannotTransferFunds("Cannot transfer funds, because account " + fundsWereTransferred.from() + " is closed.");
            }

            balance = new Balance(balance.toDouble() - fundsWereTransferred.amount().toDouble());
        } else {
            balance = new Balance(balance.toDouble() + fundsWereTransferred.amount().toDouble());
        }
    }

    public void close() throws AccountCannotBeClosed {
        final AccountWasClosed accountWasClosed = new AccountWasClosed(id);
        apply(accountWasClosed);
        producedEvents.add(accountWasClosed);
    }

    private void apply(AccountWasClosed accountWasClosed) throws AccountCannotBeClosed {
        if (this.status == AccountStatus.CLOSED) {
            throw new AccountCannotBeClosed("Account '" + accountWasClosed.accountId() + "' is already closed.");
        }

        this.status = AccountStatus.CLOSED;
    }

    public void reopen() throws AccountCannotBeReopened {
        final AccountWasReopened accountWasReopened = new AccountWasReopened(id);
        apply(accountWasReopened);
        producedEvents.add(accountWasReopened);
    }

    private void apply(AccountWasReopened accountWasReopened) throws AccountCannotBeReopened {
        if (this.status == AccountStatus.OPENED) {
            throw new AccountCannotBeReopened("Account '" + accountWasReopened.accountId() + "' is already opened.");
        }

        this.status = AccountStatus.OPENED;
    }

    public Number getNumber() {
        return number;
    }

    public Balance getBalance() {
        return balance;
    }

    public User getOwner() {
        return owner;
    }

    public AccountId id() {
        return id;
    }

    public Number number() {
        return number;
    }

    public User owner() {
        return owner;
    }

    public Balance balance() {
        return balance;
    }

    public long version() {
        return version;
    }

    public AccountStatus status() {
        return status;
    }
}
