package com.safetynet.alerts.service;


import com.safetynet.alerts.model.Person;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Any class that handles searching and editing person data
 *
 */
public interface IPersonService {

    boolean create(Person person);

    boolean update(Person person);

    boolean delete(Map<String, String> personId);

    Optional<List<Person>> findAll();

    Optional<List<Person>> findByAddress(String address);

    Optional<List<Person>> findByName(String firstName, String lastName);
}
