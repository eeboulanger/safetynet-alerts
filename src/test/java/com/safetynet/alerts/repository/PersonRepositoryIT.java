package com.safetynet.alerts.repository;

import com.safetynet.alerts.DataPrepareService;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersonRepositoryIT {

    @Autowired
    private PersonRepository repository;

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

        private DataPrepareService dataPrepareService;

        @BeforeEach
        public void setUp() {
            dataPrepareService = new DataPrepareService();
        }

        @AfterEach
        public void tearDown() throws IOException {
            dataPrepareService.resetData();
        }

        @Test
        public void createNewPersonTest() {
            Person personCreated = new Person(
                    "Test create",
                    "Smith",
                    "address",
                    "city",
                    1234,
                    "111",
                    "email"
            );

            boolean result = repository.create(personCreated);

            assertTrue(result);
        }

        @Test
        public void createNewPerson_whenExistAlready_shouldReturnFalse() {
            Person personDuplicated = dataPrepareService.getPerson(0);
            repository.create(personDuplicated);
            boolean result = repository.create(personDuplicated);

            assertFalse(result);
        }

        @Test
        public void updatePersonTest() {
            Person personUpdated = dataPrepareService.getPerson(1);
            repository.create(personUpdated);
            personUpdated.setAddress("Updated address");

            boolean result = repository.update(personUpdated);

            assertTrue(result);
        }

        @Test
        public void updatePerson_whenNoSuchPerson_shouldFail() {
            Person personUnexisting = new Person();
            personUnexisting.setFirstName("No such person");
            personUnexisting.setLastName("Test");

            boolean result = repository.update(personUnexisting);

            assertFalse(result);
        }

        @Test
        public void deletePersonTest() {
            Person personDeleted = dataPrepareService.getPerson(2);
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
            Person personUnexisting = new Person();
            personUnexisting.setFirstName("No such person");
            personUnexisting.setLastName("Test");

            Map<String, String> personId = Map.of(
                    "firstName", personUnexisting.getFirstName(),
                    "lastName", personUnexisting.getLastName()
            );
            boolean result = repository.delete(personId);

            assertFalse(result);
        }
    }
}
