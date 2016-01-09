package it.softwarelabs.bank.domain.account;

public class Balance {

    private Double value;

    public Balance(Double value) {
        this.value = value;
    }

    public Double toDouble() {
        return value;
    }
}
