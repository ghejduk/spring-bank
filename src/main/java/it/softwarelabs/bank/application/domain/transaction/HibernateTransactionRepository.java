package it.softwarelabs.bank.application.domain.transaction;

import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.transaction.Transaction;
import it.softwarelabs.bank.domain.transaction.TransactionId;
import it.softwarelabs.bank.domain.transaction.TransactionRepository;
import it.softwarelabs.collection.Collection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public final class HibernateTransactionRepository implements TransactionRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public HibernateTransactionRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Transaction single(TransactionId id) {
        return null;
    }

    public Collection<Transaction> findByAccount(Number number) {
        return null;
    }

    public void add(Transaction transaction) {
        session().save(transaction);
    }

    public void update(Transaction transaction) {
        session().merge(transaction);
    }

    private Session session() {
        return sessionFactory.getCurrentSession();
    }
}
