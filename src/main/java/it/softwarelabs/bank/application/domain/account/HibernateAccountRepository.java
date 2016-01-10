package it.softwarelabs.bank.application.domain.account;

import it.softwarelabs.bank.domain.account.Account;
import it.softwarelabs.bank.domain.account.AccountRepository;
import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.user.User;
import it.softwarelabs.collection.Collection;
import it.softwarelabs.collection.HibernateCollection;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public final class HibernateAccountRepository implements AccountRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public HibernateAccountRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Account findByNumber(Number number) {
        return (Account) session().createCriteria(Account.class)
                .add(Restrictions.eq("number", number))
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public Collection<Account> findByOwner(User owner) {
        Criteria criteria = session().createCriteria(Account.class);
        criteria.add(Restrictions.eq("owner", owner));
        return new HibernateCollection<>(criteria.list());
    }

    public void add(Account account) {
        session().save(account);
    }

    public void update(Account account) {
        session().save(account);
    }

    private Session session() {
        return sessionFactory.getCurrentSession();
    }
}
