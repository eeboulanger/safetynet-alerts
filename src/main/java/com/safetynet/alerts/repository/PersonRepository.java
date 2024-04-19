package com.safetynet.alerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.IJsonDataReader;
import com.safetynet.alerts.util.JsonDataReaderFromFile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PersonRepository implements DataRepository<Person> {

    IJsonDataReader reader = new JsonDataReaderFromFile();

    @Override
    public Optional<List<Person>> findAll() {
        return reader.findAll("persons", new TypeReference<>() {
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

    public Optional<List<Person>> findByAddress(String address) {

        Optional<List<Person>> optionalList = this.findAll();
        return optionalList.map(persons -> persons.stream()
                .filter(f -> f.getAddress().equals(address))
                .collect(Collectors.toList()));
    }
}
