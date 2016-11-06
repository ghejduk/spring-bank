package it.softwarelabs.bank.domain.user;

import java.io.Serializable;
import java.util.UUID;

public class UserId implements Serializable {

    private UUID id;

    public UserId() {
        this.id = UUID.randomUUID();
    }

    public UserId(String id) {
        this.id = UUID.fromString(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public UUID value() {
        return id;
    }
}
