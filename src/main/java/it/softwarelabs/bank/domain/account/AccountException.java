package it.softwarelabs.bank.domain.account;

public abstract class AccountException extends Throwable {

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Throwable previous) {
        super(message, previous);
    }
}
