package it.softwarelabs.bank.application.domain.user;

import it.softwarelabs.bank.domain.user.PasswordEncoder;
import it.softwarelabs.bank.domain.user.PasswordHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BCryptEncoder implements PasswordEncoder {

    private BCryptPasswordEncoder encoder;

    @Autowired
    public BCryptEncoder(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public PasswordHash encode(String password) {
        return new PasswordHash(encoder.encode(password));
    }

    public boolean isValid(String password, PasswordHash passwordHash) {
        return encoder.matches(password, passwordHash.toString());
    }
}
