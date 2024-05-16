package com.safetynet.alerts.service;


import com.safetynet.alerts.model.Person;

import java.util.List;
import java.util.Optional;

/**
 * Any service that handles searching and editing person data
 */
public interface IPersonService extends ICrudService<Person> {

    Optional<List<Person>> findByAddress(String address);

    Optional<List<Person>> findByName(String firstName, String lastName);
}
