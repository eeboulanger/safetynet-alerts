package com.safetynet.alerts.repository;

import java.util.List;
import java.util.Optional;

/**
 * For any class who interacts with data source
 * @param <T> is the java object
 */
public interface DataRepository<T> {
    Optional<List<T>> findAll();

    boolean delete(T entity);

    boolean create(T entity);

    boolean update(T entity);
}
