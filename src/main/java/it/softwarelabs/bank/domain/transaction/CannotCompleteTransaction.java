package it.softwarelabs.bank.domain.transaction;

public class CannotCompleteTransaction extends Exception {

    public CannotCompleteTransaction(String message) {
        super(message);
    }
}
