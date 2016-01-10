package it.softwarelabs.collection;

import it.softwarelabs.sort.SortDirection;
import org.hibernate.criterion.Order;

import java.util.List;

public class HibernateCollection<E> implements Collection<E> {

    private List<E> list;

    public HibernateCollection(List<E> list) {
        this.list = list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> all() {
        return list;
    }

    @Override
    public Collection<E> slice(int from, int size) {
        return new HibernateCollection<>(list);
    }

    @Override
    public Collection<E> slice(int size) {
        return slice(0, size);
    }

    @Override
    public Collection<E> sort(String property, SortDirection direction) {
//        if (direction == SortDirection.ASC) {
//            criteria.addOrder(Order.asc(property));
//        } else {
//            criteria.addOrder(Order.desc(property));
//        }

        return new HibernateCollection<>(list);
    }

    @Override
    public Collection<E> sort(String property) {
        return sort(property, SortDirection.ASC);
    }
}
