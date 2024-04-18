package com.safetynet.alerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.JsonDataReader;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepository implements DataRepository<Person> {

    @Override
    public Optional<List<Person>> findAll() {
        return JsonDataReader.findAll("persons", new TypeReference<List<Person>>() {
        });
    }

    @Override
    public void delete(Person entity) {

    }

    @Override
    public void create(Person entity) {

    }

    @Override
    public void update(Person entity) {

    }
}
