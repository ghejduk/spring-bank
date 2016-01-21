package it.softwarelabs.bank.domain.transaction;

import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.collection.Collection;

public interface TransactionRepository {

    Transaction single(TransactionId id);

    Collection<Transaction> findByAccount(Number number);

    void add(Transaction transaction);

    void update(Transaction transaction);
}
