package com.safetynet.alerts.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Any class that handles crud queries
 *
 * @param <T> is the java model
 */

public interface ICrudRepository<T> {
    boolean saveAll(List<T> list);

    boolean create(T entity);

    boolean update(T entity);

    boolean delete(Map<String, String> entityId);

    Optional<List<T>> findAll();
}
