package it.softwarelabs.collection;

import it.softwarelabs.sort.SortDirection;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import java.util.List;

public class HibernateCollection<T> implements Collection<T> {

    private Criteria criteria;

    public HibernateCollection(Criteria criteria) {
        this.criteria = criteria;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> all() {
        return criteria.list();
    }

    @Override
    public Collection<T> slice(int from, int size) {
        criteria.setFirstResult(from);
        criteria.setMaxResults(size);
        return new HibernateCollection<>(criteria);
    }

    @Override
    public Collection<T> slice(int size) {
        return slice(0, size);
    }

    @Override
    public Collection<T> sort(String property, SortDirection direction) {
        if (direction == SortDirection.ASC) {
            criteria.addOrder(Order.asc(property));
        } else {
            criteria.addOrder(Order.desc(property));
        }

        return new HibernateCollection<>(criteria);
    }

    @Override
    public Collection<T> sort(String property) {
        return sort(property, SortDirection.ASC);
    }
}
