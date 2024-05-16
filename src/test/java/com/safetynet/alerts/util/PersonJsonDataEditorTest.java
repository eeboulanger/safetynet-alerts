package com.safetynet.alerts.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.safetynet.alerts.model.DataContainer;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonJsonDataEditorTest {

    @Mock
    private ObjectMapper mapper;
    @Mock
    private ObjectWriter objectWriter;
    @InjectMocks
    private PersonJsonDataEditor editor;
    private static DataContainer dataContainer;
    private static Person existingPerson;

    @Nested
    public class SuccessfulEditingTests {
        @BeforeEach
        public void setUp() throws IOException {
            //initialize data for testing
            dataContainer = new DataContainer();
            existingPerson = new Person("FirstName",
                    "LastName",
                    "address",
                    "city",
                    123,
                    "phone",
                    "email");

            List<Person> list = new ArrayList<>();
            list.add(existingPerson);
            dataContainer.setPersons(list);
            when(mapper.readValue(any(File.class), eq(DataContainer.class))).thenReturn(dataContainer);
            when(mapper.writerWithDefaultPrettyPrinter()).thenReturn(objectWriter);
            doNothing().when(objectWriter).writeValue(any(File.class), eq(dataContainer));
        }

        @Test
        public void createPersonTest() throws IOException {
            Person p = new Person();
            p.setLastName("New person");
            p.setFirstName("Test");
            boolean isCreated = editor.create(p);

            assertTrue(isCreated);
            verify(mapper).readValue(any(File.class), eq(DataContainer.class));
            verify(objectWriter).writeValue(any(File.class), eq(dataContainer));
        }

        @Test
        public void updatePersonTest() throws IOException {
            boolean isUpdated = editor.update(existingPerson);

            assertTrue(isUpdated);
            verify(mapper).readValue(any(File.class), eq(DataContainer.class));
            verify(objectWriter).writeValue(any(File.class), eq(dataContainer));
        }

        @Test
        public void deletePersonTest() throws IOException {
            boolean isDeleted = editor.delete(Map.of(
                    "firstName", existingPerson.getFirstName(),
                    "lastName", existingPerson.getLastName())
            );

            assertTrue(isDeleted);
            verify(mapper).readValue(any(File.class), eq(DataContainer.class));
            verify(objectWriter).writeValue(any(File.class), eq(dataContainer));
        }
    }

    @Nested
    public class EditingFailsTests {
        @BeforeEach
        public void setUp() throws IOException {
            //initialize data for testing
            dataContainer = new DataContainer();
            existingPerson = new Person("FirstName",
                    "LastName",
                    "address",
                    "city",
                    123,
                    "phone",
                    "email");

            List<Person> list = new ArrayList<>();
            list.add(existingPerson);
            dataContainer.setPersons(list);
            when(mapper.readValue(any(File.class), eq(DataContainer.class))).thenReturn(dataContainer);
        }

        @Test
        @DisplayName("Create person when a person with the same name exists should fail")
        public void create_whenAlreadyExists_shouldFail() {

            boolean result = editor.create(existingPerson); //person exists already

            assertFalse(result);
        }

        @Test
        @DisplayName("Update when no person with the name exists should fail")
        public void updatePerson_whenNoPersonExists_shouldFail() {
            Person p = new Person();
            p.setLastName("");
            p.setFirstName("");
            boolean result = editor.update(p); //no person exists

            assertFalse(result);
        }

        @Test
        @DisplayName("Delete when no person with the name exists should fail")
        public void deletePerson_whenNoPersonExists_shouldFail() {
            Person p = new Person();
            p.setLastName("");
            p.setFirstName("");
            boolean result = editor.delete(Map.of(
                    "firstname", p.getFirstName(),
                    "lastname", p.getLastName())
            );

            assertFalse(result);
        }

    }

    @Nested
    public class ReadValueFailsTests {
        @BeforeEach
        public void setUp() throws IOException {
            //initialize data for testing
            dataContainer = new DataContainer();
            existingPerson = new Person("FirstName",
                    "LastName",
                    "address",
                    "city",
                    123,
                    "phone",
                    "email");

            List<Person> list = new ArrayList<>();
            list.add(existingPerson);
            dataContainer.setPersons(list);
            when(mapper.readValue(any(File.class), eq(DataContainer.class))).thenThrow(IOException.class);
        }

        @Test
        @DisplayName("Create when unable to read data should fail")
        public void create_whenFailsToReadData_shouldFail() throws IOException {
            Person p = new Person();
            p.setLastName("");
            p.setFirstName("");
            boolean result = editor.create(p);

            verify(mapper).readValue(any(File.class), eq(DataContainer.class));
            assertFalse(result);
        }

        @Test
        @DisplayName("Update when unable to read data should fail")
        public void update_whenFailsToReadData_shouldFail() throws IOException {

            boolean result = editor.update(existingPerson);

            verify(mapper).readValue(any(File.class), eq(DataContainer.class));
            assertFalse(result);
        }

        @Test
        @DisplayName("Delete when unable to read data should fail")
        public void delete_whenFailsToReadData_shouldFail() throws IOException {

            boolean result = editor.delete(Map.of(
                    "firsNname", existingPerson.getFirstName(),
                    "lastName", existingPerson.getLastName()));

            verify(mapper).readValue(any(File.class), eq(DataContainer.class));
            assertFalse(result);
        }
    }


    @Nested
    public class WriteValueFailsTests {
        @BeforeEach
        public void setUp() throws IOException {
            //initialize data for testing
            dataContainer = new DataContainer();
            existingPerson = new Person("FirstName",
                    "LastName",
                    "address",
                    "city",
                    123,
                    "phone",
                    "email");

            List<Person> list = new ArrayList<>();
            list.add(existingPerson);
            dataContainer.setPersons(list);
            when(mapper.readValue(any(File.class), eq(DataContainer.class))).thenReturn(dataContainer);
            when(mapper.writerWithDefaultPrettyPrinter()).thenReturn(objectWriter);
            doThrow(IOException.class).when(objectWriter).writeValue(any(File.class), eq(dataContainer));
        }

        @Test
        @DisplayName("Create when unable to write data should fail")
        public void create_whenFailsToWriteData_shouldFail() {
            Person p = new Person();
            p.setLastName("");
            p.setFirstName("");
            boolean result = editor.create(p);

            assertFalse(result);
        }

        @Test
        @DisplayName("Update when unable to write data should fail")
        public void update_whenFailsToWriteData_shouldFail() {
            System.out.println(dataContainer.getPersons());
            boolean result = editor.update(existingPerson);

            assertFalse(result);
        }

        @Test
        @DisplayName("Delete when unable to write data should fail")
        public void delete_whenFailsToWriteData_shouldFail() {

            boolean result = editor.delete(Map.of(
                    "firstName", existingPerson.getFirstName(),
                    "lastName", existingPerson.getLastName()));

            assertFalse(result);
        }
    }
}
