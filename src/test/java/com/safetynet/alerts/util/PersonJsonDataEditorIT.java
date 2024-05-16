package com.safetynet.alerts.util;

import com.safetynet.alerts.DataPrepareService;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersonJsonDataEditorIT {

    @Autowired
    private PersonJsonDataEditor personJsonDataEditor;
    private static DataPrepareService dataPrepareService;
    Person unexistingPerson = new Person("Empty", "Empty", null, null, 111, null, null);

    @BeforeAll
    public static void setUp() {
        dataPrepareService = new DataPrepareService();
    }

    @AfterEach
    public void tearDown() throws IOException {
        dataPrepareService.resetData();
    }

    @Test
    public void createNewPersonTest() {
        Person person = new Person();
        person.setFirstName("Test");
        person.setLastName("Test");

        boolean isCreated = personJsonDataEditor.create(person);

        assertTrue(isCreated);
    }

    @Test
    @DisplayName("Create new person when person already exists in file should fail")
    public void createNewPersonWhenAlreadyExists_thenReturnFalse() {
        Person existingPerson = dataPrepareService.getPerson(0);

        boolean isCreated = personJsonDataEditor.create(existingPerson);

        assertFalse(isCreated);
    }

    @Test
    public void updatePersonTest() {
        Person person = dataPrepareService.getPerson(0);
        person.setCity("New city");

        boolean isUpdated = personJsonDataEditor.update(person);

        assertTrue(isUpdated);
    }

    @Test
    @DisplayName("Updating person when no person with the name exists in the file should return false")
    public void updatePerson_whenNoPerson_shouldReturnFalse() {
        boolean isUpdated = personJsonDataEditor.update(unexistingPerson);

        assertFalse(isUpdated);
    }

    @Test
    public void deleteNewPersonTest() {
        Person person = dataPrepareService.getPerson(0);
        Map<String, String> personId = Map.of(
                "firstName", person.getFirstName(),
                "lastName", person.getLastName()
        );

        boolean isDeleted = personJsonDataEditor.delete(personId);

        assertTrue(isDeleted);
    }

    @Test
    @DisplayName("delete person when no person with name in file should fail")
    public void deleteNewPerson_whenNoPersonWithName_shouldReturnFalse() {
        Map<String, String> personId = Map.of(
                "firstName", unexistingPerson.getFirstName(),
                "lastName", unexistingPerson.getLastName()
        );

        boolean isDeleted = personJsonDataEditor.delete(personId);

        assertFalse(isDeleted);
    }
}
