package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Any service that implements crud queries
 */
public interface ICrudService<T> {
    boolean create(T entity);

    boolean update(T entity);

    boolean delete(Map<String, String> id);

    Optional<List<T>> findAll();
}
