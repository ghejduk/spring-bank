package it.softwarelabs.collection;

import it.softwarelabs.sort.SortDirection;

public interface Collection<T> {

    T[] all();

    Collection<T> slice(int from, int size);

    Collection<T> slice(int size);

    Collection<T> sort(String property, SortDirection direction);

    Collection<T> sort(String property);
}
