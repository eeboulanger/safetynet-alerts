package com.safetynet.alerts.repository;

import java.util.List;
import java.util.Optional;

/**
 * For any class who interacts with data source
 * @param <T> is the java object
 */
public interface DataRepository<T> {
    Optional<List<T>> findAll();

    void delete(T entity);

    void create(T entity);

    void update(T entity);
}
