package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonRepositoryTest {

    private final PersonRepository repository = new PersonRepository();
    private Person person;

    @BeforeEach
    public void setUp() {
        person = new Person(
                "Mathilda",
                "Rose",
                "Halmart street",
                "Denver",
                123,
                "111-222-333",
                "mathilda@mail.com"
        );
        repository.saveAll(new ArrayList<>(List.of(person)));
    }

    @Test
    public void createTest() {
        Person newPerson = new Person();
        newPerson.setFirstName("New person");
        newPerson.setLastName("Test");

        boolean isCreated = repository.create(newPerson);

        assertTrue(isCreated);
    }

    @Test
    public void updateTest() {
        person.setCity("Washington");
        boolean isUpdated = repository.update(person);

        assertTrue(isUpdated);
    }

    @Test
    public void deletedTest() {
        Map<String, String> map = Map.of(
                "firstName", person.getFirstName(),
                "lastName", person.getLastName()
        );

        boolean isDeleted = repository.delete(map);

        assertTrue(isDeleted);
    }

    @Test
    public void findAllTest() {
        Optional<List<Person>> optional = repository.findAll();

        assertTrue(optional.isPresent());
        assertTrue(optional.get().contains(person));
    }

    @Test
    public void findByAddressTest() {
        Optional<List<Person>> result = repository.findByAddress(person.getAddress());

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(person));
    }

    @Test
    public void findByNameTest() {

        Optional<List<Person>> result = repository.findByName(person.getFirstName(), person.getLastName());

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(person));
    }

    @Test
    @DisplayName("Create when person already exists should fail")
    public void createFailsTest() {
        boolean isCreated = repository.create(person);

        assertFalse(isCreated);
    }

    @Test
    @DisplayName("Update person when no person with the name exists should fail")
    public void updateFailsTest() {
        Person noPerson = new Person();
        noPerson.setFirstName("No such person");
        noPerson.setLastName("Test");

        boolean isUpdated = repository.update(noPerson);

        assertFalse(isUpdated);
    }

    @Test
    @DisplayName("Delete when no person with the name exists should fail")
    public void deletedFailsTest() {
        Map<String, String> map = Map.of(
                "firstname", "No such person",
                "lastname", "Test"
        );
        boolean isDeleted = repository.delete(map);

        assertFalse(isDeleted);
    }

    @Test
    @DisplayName("Find all when empty person list should return empty")
    public void findAllFailsTest() {
        repository.saveAll(new ArrayList<>());
        Optional<List<Person>> optional = repository.findAll();

        assertTrue(optional.isPresent());
        assertTrue(optional.get().isEmpty());
    }

    @Test
    @DisplayName("Find by address when no result should return empty optional")
    public void findByAddressFailsTest() {
        Optional<List<Person>> result = repository.findByAddress("No existing address");

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Find by name when no result should return empty optional")
    public void findByNameFailsTest() {
        Optional<List<Person>> result = repository.findByName("No existing person", "test");

        assertTrue(result.isEmpty());
    }
}
