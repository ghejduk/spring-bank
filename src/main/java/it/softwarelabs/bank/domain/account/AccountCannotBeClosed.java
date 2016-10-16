package it.softwarelabs.bank.domain.account;

public final class AccountCannotBeClosed extends AccountException {

    public AccountCannotBeClosed(String message) {
        super(message);
    }

    public AccountCannotBeClosed(String message, Throwable previous) {
        super(message, previous);
    }
}
