package it.softwarelabs.bank.application.domain.user;

import it.softwarelabs.bank.domain.user.Email;
import it.softwarelabs.bank.domain.user.User;
import it.softwarelabs.bank.domain.user.UserId;
import it.softwarelabs.bank.domain.user.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public final class HibernateUserRepository implements UserRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public HibernateUserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public User findById(UserId id) {
        return session().get(User.class, id);
    }

    public User findByEmail(Email email) {
        return (User) session()
                .createCriteria(User.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    public void add(User user) {
        session().save(user);
    }

    public void update(User user) {
        session().save(user);
    }

    private Session session() {
        return sessionFactory.getCurrentSession();
    }
}
