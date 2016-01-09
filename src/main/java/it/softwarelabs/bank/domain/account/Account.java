package it.softwarelabs.bank.domain.account;

import it.softwarelabs.bank.domain.transaction.CannotCompleteTransaction;
import it.softwarelabs.bank.domain.transaction.Transaction;
import it.softwarelabs.bank.domain.user.User;

public class Account {

    private Number number;
    private User owner;
    private Balance balance;

    private Account(Number number, User owner, Money deposit) {
        this.number = number;
        this.owner = owner;
        this.balance = new Balance(deposit.toDouble());
    }

    public static Account open(Number number, User owner, Money deposit) {
        return new Account(number, owner, deposit);
    }

    public Number getNumber() {
        return number;
    }

    public Balance getBalance() {
        return balance;
    }

    public Transaction transferFunds(Money amount, Number recipient) throws AccountException {
        Double currentAmount = balance.toDouble();
        Double requestedCharge = amount.toDouble();
        Double newBalance = currentAmount - requestedCharge;

        if (newBalance < 0) {
            String format = "Account %s has no enough funds (%f) to make the transfer. Requested amount: %f";
            String message = String.format(format, number.toString(), currentAmount, requestedCharge);
            throw new NoEnoughFounds(message);
        }

        balance = new Balance(newBalance);

        try {
            return Transaction.create(number, recipient, amount);
        } catch (CannotCompleteTransaction e) {
            throw new CannotTransferFunds("Cannot transfer funds due to transaction failure.", e);
        }
    }

    public User getOwner() {
        return owner;
    }
}
