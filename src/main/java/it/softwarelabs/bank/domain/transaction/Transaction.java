package it.softwarelabs.bank.domain.transaction;

import it.softwarelabs.bank.domain.account.Money;
import it.softwarelabs.bank.domain.account.Number;
import org.joda.time.DateTime;

public class Transaction {

    private TransactionId id;
    private DateTime date;
    private Number from;
    private Number to;
    private Money amount;

    protected Transaction() {
    }

    private Transaction(Number from, Number to, Money amount) throws CannotCompleteTransaction {
        if (from.equals(to)) {
            String format = "Transaction must involve two different account numbers. Given number: '%s'.";
            String message = String.format(format, from.toString());
            throw new CannotCompleteTransaction(message);
        }

        this.id = new TransactionId();
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.date = DateTime.now();
    }

    public static Transaction create(Number from, Number to, Money amount) throws CannotCompleteTransaction {
        return new Transaction(from, to, amount);
    }

    public DateTime date() {
        return date;
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

    public TransactionId id() {
        return id;
    }
}
