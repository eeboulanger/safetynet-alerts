package com.safetynet.alerts.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Any service managing data retrieval and updating operations to entities
 */
public interface ICrudService<T> {
    boolean create(T entity);
    boolean update(T entity);
    boolean delete(Map<String, String> id);
    Optional<List<T>> findAll();
}
