package it.softwarelabs.bank.application.domain.transaction;

import org.joda.time.DateTime;

import java.util.UUID;

public final class TransactionView {

    private final UUID id;
    private final double amount;
    private final DateTime createdAt;
    private final UUID from;
    private final UUID to;
    private final int status;

    public TransactionView(UUID id, double amount, DateTime createdAt, UUID from, UUID to, int status) {
        this.id = id;
        this.amount = amount;
        this.createdAt = createdAt;
        this.from = from;
        this.to = to;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public UUID getFrom() {
        return from;
    }

    public UUID getTo() {
        return to;
    }

    public int getStatus() {
        return status;
    }
}
