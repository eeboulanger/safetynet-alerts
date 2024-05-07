package com.safetynet.alerts.service;


import com.safetynet.alerts.model.Person;

/**
 * Any class that handles editing person data
 *
 * @param
 */
public interface IPersonService {

    boolean create(Person person);

    boolean update(Person person);

    boolean delete(Person person);
}
