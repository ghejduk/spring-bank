package it.softwarelabs.bank.domain.transaction;

public class TransactionId {

    private int id;

    public TransactionId(int id) {
        if (id < 1) {
            String message = String.format("Transaction id cannot be less than 1. Given value: %d", id);
            throw new IllegalArgumentException(message);
        }

        this.id = id;
    }

    public int toInt() {
        return id;
    }
}
