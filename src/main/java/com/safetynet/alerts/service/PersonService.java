package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public boolean delete(Person person) {
        return personRepository.delete(person);
    }
}
