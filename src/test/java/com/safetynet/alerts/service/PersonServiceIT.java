package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.AfterEach;
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
    private Person person = new Person(
            "Person test",
            "Smith",
            "Address",
            "City",
            12345,
            "1111",
            "Email");

    @AfterEach
    public void reset() {
        Map<String, String> personId = Map.of(
                "firstName", person.getFirstName(),
                "lastName", person.getLastName()
        );

        personService.delete(personId);
    }

    @Test
    @DisplayName("Given there is no person with the name, then write new person to file")
    public void createPerson_whenNewPerson_thenWritePersonToFile() {

        boolean result = personService.create(person);

        assertTrue(result);
    }

    @Test
    @DisplayName("Given there is a person with the name, then update person in existing file")
    public void updatePerson_whenPersonExists_thenUpdatePersonToFile() {
        personService.create(person);

        person = new Person(
                "Person test",
                "Smith",
                "New address",
                "New city",
                12345,
                "1111",
                "New email"
        );

        boolean isUpdated = personService.update(person);

        Optional<List<Person>> optional = personService.findByName(person.getFirstName(), person.getLastName());
        Person result = new Person();
        if (optional.isPresent()) {
            result = optional.get().get(0);
        }

        assertTrue(isUpdated);
        assertEquals("New address", result.getAddress());
        assertEquals("New city", result.getCity());
        assertEquals("New email", result.getEmail());
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

        Map<String, String> personId = Map.of(
                "firstName", person.getFirstName(),
                "lastName", person.getLastName()
        );

        boolean result = personService.delete(personId);

        assertTrue(result);
    }
}
