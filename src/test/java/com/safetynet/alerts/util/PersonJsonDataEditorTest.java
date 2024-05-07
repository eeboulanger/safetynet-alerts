package com.safetynet.alerts.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataContainer;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PersonJsonDataEditorTest {

    private static final PersonJsonDataEditor personJsonDataEditor = new PersonJsonDataEditor();
    private static final String JSON_DATA_PATH = "./data/data.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    private final File jsonFile = new File(JSON_DATA_PATH);
    private static Person person;
    private static Person duplicatePerson;
    private static Person unexistingPerson;
    private DataContainer data;

    @BeforeAll
    public static void setUp() {
        person = new Person(
                "New person",
                "Smith",
                "address",
                "city",
                123,
                "phone",
                "@mail.com"
        );

        duplicatePerson = new Person(
                "Duplicate",
                "Hayden",
                "address",
                "city",
                123,
                "phone",
                "@mail.com"
        );
        unexistingPerson = new Person(
                "No person with name",
                "Smith",
                "address",
                "city",
                123,
                "phone",
                "@mail.com"
        );
    }

    @BeforeEach
    public void setUpData() throws IOException {
        data = mapper.readValue(jsonFile, DataContainer.class);
    }

    @AfterAll
    public static void tearDown() {
        Map<String, String> personId = Map.of(
                "firstName", duplicatePerson.getFirstName(),
                "lastName", duplicatePerson.getLastName());

        personJsonDataEditor.delete(personId);
    }

    @Test
    public void createNewPersonTest() throws IOException {

        int numberOfPersons = data.getPersons().size();

        boolean isCreated = personJsonDataEditor.create(person);
        data = mapper.readValue(jsonFile, DataContainer.class);

        assertTrue(isCreated);
        assertEquals(numberOfPersons + 1, data.getPersons().size());
    }

    @Test
    @DisplayName("Create new person when person already exists in file should fail")
    public void createNewPersonWhenAlreadyExists_thenReturnFalse() throws IOException {
        personJsonDataEditor.create(duplicatePerson);
        data = mapper.readValue(jsonFile, DataContainer.class);
        int numberOfPersons = data.getPersons().size();

        boolean isCreated = personJsonDataEditor.create(duplicatePerson);
        data = mapper.readValue(jsonFile, DataContainer.class);

        assertFalse(isCreated);
        assertEquals(numberOfPersons, data.getPersons().size());
    }

    @Test
    public void updatePersonTest() throws IOException {
        person.setCity("New city");

        boolean isUpdated = personJsonDataEditor.update(person);
        data = mapper.readValue(jsonFile, DataContainer.class);

        List<Person> personList = data.getPersons();
        String updatedCity = personList.get(personList.size() - 1).getCity();

        assertTrue(isUpdated);
        assertEquals("New city", updatedCity);
    }

    @Test
    @DisplayName("Updating person when no person with the name exists in the file should return false")
    public void updatePerson_whenNoPerson_shouldReturnFalse() {

        boolean isUpdated = personJsonDataEditor.update(unexistingPerson);

        assertFalse(isUpdated);
    }

    @Test
    public void deleteNewPersonTest() throws IOException {
        int numberOfPersons = data.getPersons().size();

        Map<String, String> personId = Map.of(
                "firstName", person.getFirstName(),
                "lastName", person.getLastName()
        );

        boolean isDeleted = personJsonDataEditor.delete(personId);
        data = mapper.readValue(jsonFile, DataContainer.class);

        assertTrue(isDeleted);
        assertEquals(numberOfPersons - 1, data.getPersons().size());
    }

    @Test
    @DisplayName("delete person when no person with name in file should fail")
    public void deleteNewPerson_whenNoPersonWithName_shouldReturnFalse() throws IOException {
        int numberOfPersons = data.getPersons().size();

        Map<String, String> personId = Map.of(
                "firstName", unexistingPerson.getFirstName(),
                "lastName", unexistingPerson.getLastName()
        );

        boolean isDeleted = personJsonDataEditor.delete(personId);
        data = mapper.readValue(jsonFile, DataContainer.class);

        assertFalse(isDeleted);
        assertEquals(numberOfPersons, data.getPersons().size());
    }
}
