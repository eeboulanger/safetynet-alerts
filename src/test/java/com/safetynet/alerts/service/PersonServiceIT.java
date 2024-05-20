package com.safetynet.alerts.service;

import com.safetynet.alerts.config.DataInitializer;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PersonServiceIT {
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository repository;
    @Autowired
    private DataInitializer dataInitializer;
    private Person person;

    @BeforeEach
    public void setUp() {
        //Initialize data for testing
        dataInitializer.run();

        person = repository.findAll().isPresent() ? repository.findAll().get().get(0) : new Person();
    }

    @Test
    @DisplayName("Given there is no person with the name, then save new person")
    public void createPerson_whenNewPerson_thenSavePerson() {
        Person newPerson = new Person(
                "Emma",
                "Smith",
                "address",
                "city",
                123,
                "phone",
                "email"
        );
        boolean result = personService.create(newPerson);

        assertTrue(result);
    }

    @Test
    @DisplayName("Given there is a person with the name, then update person")
    public void updatePerson_whenPersonExists_thenUpdatePerson() {
        person.setCity("Denver");
        boolean isUpdated = personService.update(person);

        assertTrue(isUpdated);
    }

    @Test
    @DisplayName("Given there is a person with the name, then delete the person")
    public void deletePerson_whenPersonExists_thenDeletePerson() {
        Map<String, String> personId = Map.of(
                "firstName", person.getFirstName(),
                "lastName", person.getLastName()
        );

        boolean result = personService.delete(personId);

        assertTrue(result);
    }

    @Test
    public void findAllTest() {
        Optional<List<Person>> result = personService.findAll();
        assertTrue(result.isPresent());
        assertEquals(23, result.get().size());
    }

    @Test
    public void findByName() {
        Optional<List<Person>> result = personService.findByName(person.getFirstName(), person.getLastName());
        assertTrue(result.isPresent());
        assertTrue(result.get().contains(person));
    }
    @Test
    public void findByAddress(){
        Optional<List<Person>> result = personService.findByAddress(person.getAddress());
        assertTrue(result.isPresent());
        assertTrue(result.get().contains(person));
    }
}
