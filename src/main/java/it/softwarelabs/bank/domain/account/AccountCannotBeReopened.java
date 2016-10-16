package it.softwarelabs.bank.domain.account;

public final class AccountCannotBeReopened extends AccountException {

    public AccountCannotBeReopened(String message) {
        super(message);
    }

    public AccountCannotBeReopened(String message, Throwable previous) {
        super(message, previous);
    }
}
