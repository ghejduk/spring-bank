package it.softwarelabs.bank.domain.account;

public class Number {

    private String number;

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
    public boolean equals(Object obj) {
        return obj instanceof Number && number.equals(obj.toString());
    }
}
