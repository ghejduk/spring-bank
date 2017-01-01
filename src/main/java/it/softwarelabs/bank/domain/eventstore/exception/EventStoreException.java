package it.softwarelabs.bank.domain.eventstore.exception;

public abstract class EventStoreException extends Exception {

    public EventStoreException() {
    }

    public EventStoreException(String message) {
        super(message);
    }

    public EventStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventStoreException(Throwable cause) {
        super(cause);
    }
}
