package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommunityService implements ICommunityService {

    @Autowired
    private PersonRepository personRepository;

    /**
     * Finds all emails of citizens
     * @param city used for searching
     * @return a list of emails or an empty list of no persons found
     */
    @Override
    public Set<String> getAllEmails(String city) {
        Optional<List<Person>> optionalList = personRepository.findAll();

        return optionalList.map(personList -> personList.stream()
                .filter(person -> person.getCity().equals(city))
                .map(Person::getEmail)
                .collect(Collectors.toSet())).orElseGet(HashSet::new);
    }
}
