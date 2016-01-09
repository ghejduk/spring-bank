package it.softwarelabs.bank.domain.user;

public interface PasswordEncoder {

    PasswordHash encode(String password);

    boolean isValid(String password, PasswordHash passwordHash);
}
