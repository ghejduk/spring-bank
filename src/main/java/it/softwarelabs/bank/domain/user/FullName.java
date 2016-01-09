package it.softwarelabs.bank.domain.user;

public class FullName {

    private String firstName;
    private String lastName;

    protected FullName() {
    }

    public FullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }
}
