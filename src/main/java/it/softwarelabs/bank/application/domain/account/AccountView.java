package it.softwarelabs.bank.application.domain.account;

import java.util.UUID;

public final class AccountView {

    private final UUID id;
    private final String number;
    private final double balance;
    private final UUID ownerId;

    public AccountView(UUID id, String number, double balance, UUID ownerId) {
        this.id = id;
        this.number = number;
        this.balance = balance;
        this.ownerId = ownerId;
    }

    public UUID getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    public UUID getOwnerId() {
        return ownerId;
    }
}
