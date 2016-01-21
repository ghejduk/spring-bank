package it.softwarelabs.bank.domain.transaction;

public class TransactionAlreadyCompleted extends Exception {

    public TransactionAlreadyCompleted(String message) {
        super(message);
    }
}
