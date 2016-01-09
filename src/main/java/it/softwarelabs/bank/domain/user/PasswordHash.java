package it.softwarelabs.bank.domain.user;

public class PasswordHash {

    private String value;

    protected PasswordHash() {
        this.value = "";
    }

    public PasswordHash(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
