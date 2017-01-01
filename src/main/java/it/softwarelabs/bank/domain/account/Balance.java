package it.softwarelabs.bank.domain.account;

import java.io.Serializable;

public final class Balance implements Serializable {

    private Double value;

    public Balance(Double value) {
        this.value = value;
    }

    protected Balance() {
    }

    public Double toDouble() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
