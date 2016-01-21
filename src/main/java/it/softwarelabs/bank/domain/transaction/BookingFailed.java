package it.softwarelabs.bank.domain.transaction;

public class BookingFailed extends RuntimeException {

    public BookingFailed(String message, Throwable previous) {
        super(message, previous);
    }
}
