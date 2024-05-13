package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Any class that handles queries to read and edit person data
 */
public interface IPersonRepository {

    Optional<List<Person>> findAll();

    boolean delete(Map<String, String> personId);

    boolean create(Person person);

    boolean update(Person person);

    Optional<List<Person>> findByAddress(String address);

    Optional<List<Person>> findByName(String firstName, String lastName);
}
