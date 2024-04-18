package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class PersonRepositoryTest {

    private final PersonRepository repository = new PersonRepository();

    @Test
    public void getAllPersonsFromJsonTest() {
        Optional<List<Person>> optional = repository.findAll();
        List<Person> persons = new ArrayList<>();
        if (optional.isPresent()) {
            persons = optional.get();
        }
        assertFalse(persons.isEmpty());
        assertEquals("John", persons.get(0).getFirstName());
        assertEquals("Boyd", persons.get(0).getLastName());
        assertEquals("1509 Culver St", persons.get(0).getAddress());
        assertEquals("Culver", persons.get(0).getCity());
        assertEquals(97451, persons.get(0).getZip());
        assertEquals("841-874-6512", persons.get(0).getPhone());
        assertEquals("jaboyd@email.com", persons.get(0).getEmail());

    }
}
