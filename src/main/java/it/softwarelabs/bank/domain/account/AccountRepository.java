package it.softwarelabs.bank.domain.account;

import it.softwarelabs.bank.domain.user.UserId;
import it.softwarelabs.collection.Collection;

public interface AccountRepository {

    Account findByNumber(Number number);

    Collection<Account> findByUserId(UserId id);

    void add(Account account);

    void update(Account account);
}
