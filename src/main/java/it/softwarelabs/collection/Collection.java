package it.softwarelabs.collection;

import it.softwarelabs.sort.SortDirection;

import java.util.List;

public interface Collection<E> {

    List<E> all();

    Collection<E> slice(int from, int size);

    Collection<E> slice(int size);

    Collection<E> sort(String property, SortDirection direction);

    Collection<E> sort(String property);
}
