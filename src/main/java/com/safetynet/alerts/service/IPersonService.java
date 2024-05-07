package com.safetynet.alerts.service;


import com.safetynet.alerts.model.Person;

import java.util.List;
import java.util.Optional;

/**
 * Any class that handles searching and editing person data
 *
 * @param
 */
public interface IPersonService {

    boolean create(Person person);

    boolean update(Person person);

    boolean delete(Person person);

    Optional<List<Person>> findAll();

    Optional<List<Person>> findByAddress(String address);

    Optional<List<Person>> findByName(String firstName, String lastName);
}
