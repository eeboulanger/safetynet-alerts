package com.safetynet.alerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.IJsonDataEditor;
import com.safetynet.alerts.util.IJsonDataReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonRepositoryTest {

    @Mock
    private IJsonDataEditor<Person> editor;
    @Mock
    private IJsonDataReader reader;
    @InjectMocks
    private PersonRepository repository;
    private Person person;

    @BeforeEach
    public void setUp() {
        person = new Person(
                "Person under test",
                "test",
                "address",
                "city",
                123,
                "phone",
                "email"
        );
    }

    @Test
    public void createTest() {
        when(editor.create(person)).thenReturn(true);

        boolean isCreated = repository.create(person);

        verify(editor).create(person);
        assertTrue(isCreated);
    }

    @Test
    public void updateTest() {
        when(editor.update(person)).thenReturn(true);

        boolean isUpdated = repository.update(person);

        verify(editor).update(person);
        assertTrue(isUpdated);
    }

    @Test
    public void deletedTest() {
        Map<String, String> map = Map.of(
                "firstname", person.getFirstName(),
                "lastname", person.getLastName()
        );
        when(editor.delete(map)).thenReturn(true);

        boolean isDeleted = repository.delete(map);

        verify(editor).delete(map);
        assertTrue(isDeleted);
    }

    @Test
    public void findAllTest() {
        List<Person> personList = List.of(person);

        when(reader.findAll(eq("persons"), any(TypeReference.class))).thenReturn(Optional.of(personList));

        Optional<List<Person>> optional = repository.findAll();

        verify(reader).findAll(eq("persons"), any(TypeReference.class));
        assertTrue(optional.isPresent());
        assertTrue(optional.get().contains(person));
    }

    @Test
    public void findByAddressTest() {
        List<Person> personList = List.of(person);
        when(reader.findAll(eq("persons"), any(TypeReference.class))).thenReturn(Optional.of(personList));

        Optional<List<Person>> result = repository.findByAddress("address");

        verify(reader).findAll(eq("persons"), any(TypeReference.class));
        assertTrue(result.isPresent());
        assertTrue(result.get().contains(person));
    }

    @Test
    public void findByNameTest() {
        List<Person> personList = List.of(person);
        when(reader.findAll(eq("persons"), any(TypeReference.class))).thenReturn(Optional.of(personList));

        Optional<List<Person>> result = repository.findByName("Person under test", "test");

        verify(reader).findAll(eq("persons"), any(TypeReference.class));
        assertTrue(result.isPresent());
        assertTrue(result.get().contains(person));
    }

    @Test
    public void createFailsTest() {
        when(editor.create(person)).thenReturn(false);

        boolean isCreated = repository.create(person);

        verify(editor).create(person);
        assertFalse(isCreated);
    }

    @Test
    public void updateFailsTest() {
        when(editor.update(person)).thenReturn(false);

        boolean isUpdated = repository.update(person);

        verify(editor).update(person);
        assertFalse(isUpdated);
    }

    @Test
    public void deletedFailsTest() {
        Map<String, String> map = Map.of(
                "firstname", person.getFirstName(),
                "lastname", person.getLastName()
        );
        when(editor.delete(map)).thenReturn(false);

        boolean isDeleted = repository.delete(map);

        verify(editor).delete(map);
        assertFalse(isDeleted);
    }

    @Test
    public void findAllFailsTest() {
        when(reader.findAll(eq("persons"), any(TypeReference.class))).thenReturn(Optional.empty());

        Optional<List<Person>> optional = repository.findAll();

        verify(reader).findAll(eq("persons"), any(TypeReference.class));
        assertTrue(optional.isEmpty());
    }

    @Test
    public void findByAddressFailsTest() {
        List<Person> personList = List.of(person);

        when(reader.findAll(eq("persons"), any(TypeReference.class))).thenReturn(Optional.of(personList));

        Optional<List<Person>> result = repository.findByAddress("No existing address");

        verify(reader).findAll(eq("persons"), any(TypeReference.class));
        assertTrue(result.isPresent());
        assertTrue(result.get().isEmpty());
    }

    @Test
    public void findByNameFailsTest() {
        List<Person> personList = List.of(person);
        when(reader.findAll(eq("persons"), any(TypeReference.class))).thenReturn(Optional.of(personList));

        Optional<List<Person>> result = repository.findByName("No existing person", "test");

        verify(reader).findAll(eq("persons"), any(TypeReference.class));
        assertTrue(result.isPresent());
        assertTrue(result.get().isEmpty());
    }
}
