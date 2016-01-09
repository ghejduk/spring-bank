package it.softwarelabs.bank.domain.user;

import it.softwarelabs.bank.domain.stereotype.Factory;

@Factory
public class UserFactory {

    public User create(String firstName, String lastName, String email) {
        FullName fullName = new FullName(firstName, lastName);
        Email userEmail = new Email(email);

        return User.register(new UserId(), fullName, userEmail);
    }

    public User create(String email) {
        return User.register(new Email(email));
    }
}
