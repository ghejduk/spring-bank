package it.softwarelabs.bank.application.domain.transaction;

import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.transaction.Transaction;
import it.softwarelabs.bank.domain.transaction.TransactionId;
import it.softwarelabs.bank.domain.transaction.TransactionRepository;
import it.softwarelabs.collection.Collection;
import it.softwarelabs.collection.HibernateCollection;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
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
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Transaction.class);
        Criterion from = Restrictions.eq("from", number);
        Criterion to = Restrictions.eq("to", number);
        criteria.add(Restrictions.or(from, to));
        criteria.addOrder(Order.desc("date"));

        return new HibernateCollection<>(session, criteria);
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
