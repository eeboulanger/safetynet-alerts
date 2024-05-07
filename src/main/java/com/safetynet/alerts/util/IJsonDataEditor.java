package com.safetynet.alerts.util;

import com.safetynet.alerts.model.Person;

/**
 * For any class editing data to an existing json file
 */
public interface IJsonDataEditor<T> {

    boolean create(T entity);

    boolean update(T entity);

    boolean delete(T entity);

}
