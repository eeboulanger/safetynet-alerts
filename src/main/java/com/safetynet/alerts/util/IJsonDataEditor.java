package com.safetynet.alerts.util;

import java.util.Map;

/**
 * For any class editing data in an existing json file
 */
public interface IJsonDataEditor<T> {

    boolean create(T entity);

    boolean update(T entity);

    boolean delete(Map<String, String> identifier);

}
