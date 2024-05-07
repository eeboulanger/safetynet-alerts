package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PersonServiceIT {

    @Autowired
    private PersonService personService;

    private Person person;

    @AfterEach
    public void reset() {
        personService.delete(person);
    }

    @Test
    @DisplayName("Given there is no person with the name, then write new person to file")
    public void createPerson_whenNewPerson_thenWritePersonToFile() {

        person = new Person(
                "New person test",
                "Smith",
                "Address",
                "City",
                12345,
                "1111",
                "Email"
        );

        boolean result = personService.create(person);

        assertTrue(result);
    }

    @Test
    @DisplayName("Given there is a person with the name, then update person in existing file")
    public void updatePerson_whenPersonExists_thenUpdatePersonToFile() {

        person = new Person(
                "Updated person test",
                "Smith",
                "Address",
                "City",
                12345,
                "1111",
                "Email"
        );
        personService.create(person);
        boolean result = personService.update(person);

        assertTrue(result);
    }

    @Test
    @DisplayName("Given there is a person with the name, then delete the person from existing file")
    public void deletePerson_whenPersonExists_thenDeletePersonFromFile() {

        person = new Person(
                "Delete person test",
                "Smith",
                "Address",
                "City",
                12345,
                "1111",
                "Email"
        );
        personService.create(person);

        boolean result = personService.delete(person);

        assertTrue(result);
    }
}
