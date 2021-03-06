package it.softwarelabs.collection;

import it.softwarelabs.sort.SortDirection;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.List;

public class HibernateCollection<E> implements Collection<E> {

    private Session session;
    private Criteria criteria;

    public HibernateCollection(Session session, Criteria criteria) {
        this.session = session;
        this.criteria = criteria;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> all() {
        List list = criteria.list();
        session.clear();
        session.close();
        return list;
    }

    @Override
    public Collection<E> slice(int from, int size) {
        return null;
    }

    @Override
    public Collection<E> slice(int size) {
        return null;
    }

    @Override
    public Collection<E> sort(String property, SortDirection direction) {
        if (direction == SortDirection.ASC) {
            criteria.addOrder(Order.asc(property));
        } else {
            criteria.addOrder(Order.desc(property));
        }

        return new HibernateCollection<>(session, criteria);
    }

    @Override
    public Collection<E> sort(String property) {
        return sort(property, SortDirection.ASC);
    }
}
