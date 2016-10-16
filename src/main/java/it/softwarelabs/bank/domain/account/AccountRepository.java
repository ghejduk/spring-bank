package it.softwarelabs.bank.domain.account;

import it.softwarelabs.bank.domain.eventstore.Aggregate;
import it.softwarelabs.bank.domain.user.User;
import it.softwarelabs.collection.Collection;

public interface AccountRepository {

    Account singleByNumber(Number number);

    Collection<Aggregate> findByOwner(User owner);

    void add(Aggregate account);

    void update(Aggregate account);
}
