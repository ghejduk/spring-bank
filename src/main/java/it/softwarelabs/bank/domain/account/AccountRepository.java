package it.softwarelabs.bank.domain.account;

import it.softwarelabs.bank.domain.user.User;
import it.softwarelabs.collection.Collection;

public interface AccountRepository {

    Account findByNumber(Number number);

    Collection<Account> findByOwner(User owner);

    void add(Account account);

    void update(Account account);
}
