package it.softwarelabs.bank.domain.account;

public class NoEnoughFounds extends AccountException {

    public NoEnoughFounds(String message) {
        super(message);
    }
}
