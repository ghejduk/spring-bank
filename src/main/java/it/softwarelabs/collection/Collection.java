package it.softwarelabs.collection;

import it.softwarelabs.sort.SortDirection;

import java.util.List;

public interface Collection<T> {

    List<T> all();

    Collection<T> slice(int from, int size);

    Collection<T> slice(int size);

    Collection<T> sort(String property, SortDirection direction);

    Collection<T> sort(String property);
}
