package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class PersonRepositoryIT {

    private static final PersonRepository repository = new PersonRepository();

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

    @Test
    public void findPersonsByAddressTest() {

        Optional<List<Person>> optional = repository.findByAddress("1509 Culver St");
        List<Person> persons = new ArrayList<>();
        if (optional.isPresent()) {
            persons = optional.get();
        }

        assertFalse(persons.isEmpty());
        assertEquals(5, persons.size());
    }

    @Nested
    public class DataEditingTests {

        private static Person personCreated;
        private static Person personDuplicated;
        private static Person personUpdated;
        private static Person personDeleted;
        private static Person personUnexisting;

        @BeforeAll
        public static void setUp() {
            personCreated = new Person(
                    "Test create",
                    "Smith",
                    "address",
                    "city",
                    1234,
                    "111",
                    "email"
            );

            personDuplicated = new Person(
                    "Test duplicate",
                    "Smith",
                    "address",
                    "city",
                    1234,
                    "111",
                    "email"
            );

            personUpdated = new Person(
                    "Test update",
                    "Smith",
                    "address",
                    "city",
                    1234,
                    "111",
                    "email"
            );

            personDeleted = new Person(
                    "Test delete",
                    "Smith",
                    "address",
                    "city",
                    1234,
                    "111",
                    "email"
            );

            personUnexisting = new Person(
                    "Test unexisting",
                    "Smith",
                    "address",
                    "city",
                    1234,
                    "111",
                    "email"
            );
        }

        @AfterAll
        public static void resetData() {

            repository.delete(Map.of(
                    "firstName", personCreated.getFirstName(),
                    "lastName", personCreated.getLastName()
            ));

            repository.delete(Map.of(
                    "firstName", personDuplicated.getFirstName(),
                    "lastName", personDuplicated.getLastName()
            ));

            repository.delete(Map.of(
                    "firstName", personUpdated.getFirstName(),
                    "lastName", personUpdated.getLastName()
            ));
        }

        @Test
        public void createNewPersonTest() {

            boolean result = repository.create(personCreated);

            assertTrue(result);
        }

        @Test
        public void createNewPerson_whenExistAlready_shouldReturnFalse() {
            repository.create(personDuplicated);
            boolean result = repository.create(personDuplicated);

            assertFalse(result);
        }

        @Test
        public void updatePersonTest() {
            repository.create(personUpdated);
            personUpdated.setAddress("Updated address");

            boolean result = repository.update(personUpdated);

            assertTrue(result);
        }

        @Test
        public void updatePerson_whenNoSuchPerson_shouldFail() {

            boolean result = repository.update(personUnexisting);

            assertFalse(result);
        }

        @Test
        public void deletePersonTest() {
            repository.create(personDeleted);

            Map<String, String> personId = Map.of(
                    "firstName", personDeleted.getFirstName(),
                    "lastName", personDeleted.getLastName()
            );

            boolean result = repository.delete(personId);

            assertTrue(result);
        }

        @Test
        public void deletePerson_whenNoSuchPerson_shouldFail() {
            Map<String, String> personId = Map.of(
                    "firstName", personUnexisting.getFirstName(),
                    "lastName", personUnexisting.getLastName()
            );
            boolean result = repository.delete(personId);

            assertFalse(result);
        }
    }
}
