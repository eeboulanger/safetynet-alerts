package com.safetynet.alerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.IJsonDataEditor;
import com.safetynet.alerts.util.IJsonDataReader;
import com.safetynet.alerts.util.JsonDataReaderFromFile;
import com.safetynet.alerts.util.PersonJsonDataEditor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PersonRepository implements DataRepository<Person> {

    IJsonDataReader reader = new JsonDataReaderFromFile();
    IJsonDataEditor<Person> editor = new PersonJsonDataEditor();

    @Override
    public Optional<List<Person>> findAll() {
        return reader.findAll("persons", new TypeReference<>() {
        });
    }

    @Override
    public boolean delete(Person person) {
        return editor.delete(person);
    }

    @Override
    public boolean create(Person person) {
        return editor.create(person);
    }

    @Override
    public boolean update(Person person) {
        return editor.update(person);
    }

    public Optional<List<Person>> findByAddress(String address) {

        Optional<List<Person>> optionalList = this.findAll();
        return optionalList.map(persons -> persons.stream()
                .filter(f -> f.getAddress().equals(address))
                .collect(Collectors.toList()));
    }

    public Optional<List<Person>> findByName(String firstName, String lastName) {
        Optional<List<Person>> optionalList = this.findAll();

        return optionalList.map(personList -> personList.stream()
                .filter(person -> person.getFirstName().equals(firstName)
                        && person.getLastName().equals(lastName))
                .collect(Collectors.toList()));
    }
}
