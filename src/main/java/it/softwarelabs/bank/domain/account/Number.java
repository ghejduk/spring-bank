package it.softwarelabs.bank.domain.account;

import java.io.Serializable;

public final class Number implements Serializable {

    private String number;

    protected Number() {
    }

    public Number(String number) throws IllegalArgumentException {
        if (!number.matches("^[A-Z0-9]*$")) {
            throw new IllegalArgumentException("Number is invalid. Only uppercase alphanumerics and dash is allowed.");
        }

        this.number = number;
    }

    @Override
    public String toString() {
        return this.number;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Number && ((Number) o).number.equals(number);
    }
}
