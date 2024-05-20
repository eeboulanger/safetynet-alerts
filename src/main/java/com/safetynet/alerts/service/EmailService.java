package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmailService implements IEmailService {
    @Autowired
    private IPersonService personService;

    /**
     * Finds all emails of citizens
     *
     * @param city used for searching
     * @return a set of unique emails or an empty list if no persons found
     */
    @Override
    public Set<String> getAllEmails(String city) {
        Optional<List<Person>> optionalList = personService.findAll();

        return optionalList.map(personList -> personList.stream()
                .filter(person -> person.getCity().equals(city))
                .map(Person::getEmail)
                .collect(Collectors.toSet())).orElseGet(HashSet::new);
    }

}
