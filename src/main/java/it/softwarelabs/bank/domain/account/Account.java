package it.softwarelabs.bank.domain.account;

import it.softwarelabs.bank.domain.transaction.Transaction;
import it.softwarelabs.bank.domain.user.User;

public class Account {

    private Number number;
    private User owner;
    private Balance balance;

    protected Account() {
    }

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

    public void bookTransaction(Transaction transaction) throws AccountException {
        if (!transaction.getFrom().equals(number) && !transaction.getTo().equals(number)) {
            String format = String.format("Account %s is not a part in this transaction %s.", number.toString(), transaction.getId().toString());
            throw new CannotTransferFunds(format);
        }

        if (transaction.getFrom().equals(number)) {
            balance = new Balance(balance.toDouble() - transaction.getAmount().toDouble());
        } else {
            balance = new Balance(balance.toDouble() + transaction.getAmount().toDouble());
        }
    }

    public User getOwner() {
        return owner;
    }
}
