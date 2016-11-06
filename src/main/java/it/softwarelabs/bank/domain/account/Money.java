package it.softwarelabs.bank.domain.account;

import java.io.Serializable;

public final class Money implements Serializable {

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
