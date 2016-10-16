package it.softwarelabs.bank.domain.account;

public final class Money {

    private Double amount;

    public Money(Double amount) {
        this.amount = amount;
    }

    protected Money() {
    }

    public Double toDouble() {
        return amount;
    }
}
