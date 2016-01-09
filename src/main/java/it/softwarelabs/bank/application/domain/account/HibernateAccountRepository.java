package it.softwarelabs.bank.application.domain.account;

import it.softwarelabs.bank.domain.account.Account;
import it.softwarelabs.bank.domain.account.AccountRepository;
import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.user.UserId;
import it.softwarelabs.collection.Collection;
import org.springframework.stereotype.Repository;

@Repository
public final class HibernateAccountRepository implements AccountRepository {

    public Account findByNumber(Number number) {
        return null;
    }

    public Collection<Account> findByUserId(UserId id) {
        return null;
    }

    public void add(Account account) {

    }

    public void update(Account account) {

    }
}
