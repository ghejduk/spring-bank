package it.softwarelabs.bank.domain.user;

import java.io.Serializable;
import java.util.UUID;

public class UserId implements Serializable {

    private String id;

    public UserId() {
        this.id = UUID.randomUUID().toString();
    }

    public UserId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}
