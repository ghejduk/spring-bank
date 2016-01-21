package it.softwarelabs.bank.domain.account;

public class CannotTransferFunds extends AccountException {

    public CannotTransferFunds(String message, Throwable throwable) {
        super(message, throwable);
    }

    public CannotTransferFunds(String message) {
        super(message);
    }
}
