package it.softwarelabs.bank.application.domain.transaction;

import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.transaction.Transaction;
import it.softwarelabs.bank.domain.transaction.TransactionId;
import it.softwarelabs.bank.domain.transaction.TransactionRepository;
import it.softwarelabs.collection.Collection;
import org.springframework.stereotype.Repository;

@Repository
public final class HibernateTransactionRepository implements TransactionRepository {

    public Transaction findById(TransactionId id) {
        return null;
    }

    public Collection<Transaction> findByAccount(Number number) {
        return null;
    }

    public void add(Transaction transaction) {

    }
}
