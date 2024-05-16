package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;

import java.util.List;
import java.util.Optional;

/**
 * Any class that handles queries to read and edit person data
 */
public interface IPersonRepository extends ICrudRepository<Person> {

    Optional<List<Person>> findByAddress(String address);

    Optional<List<Person>> findByName(String firstName, String lastName);
}
