package it.softwarelabs.bank.domain.user;

public interface UserRepository {

    User findById(UserId id);

    User findByEmail(Email email);

    void add(User user);

    void update(User user);
}
