package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PersonService implements IPersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public boolean create(Person person) {
        return personRepository.create(person);
    }

    @Override
    public boolean update(Person person) {
        return personRepository.update(person);
    }

    @Override
    public boolean delete(Map<String, String> personId) {
        return personRepository.delete(personId);
    }

    @Override
    public Optional<List<Person>> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Optional<List<Person>> findByAddress(String address) {
        return personRepository.findByAddress(address);
    }

    @Override
    public Optional<List<Person>> findByName(String firstName, String lastName) {
        return personRepository.findByName(firstName, lastName);
    }
}
