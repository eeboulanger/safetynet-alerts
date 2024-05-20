package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.IPersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @Mock
    private IPersonRepository personRepository;
    @InjectMocks
    private PersonService personService;

    private Person person;
    private List<Person> list;
    private final Map<String, String> personId = new HashMap<>();

    @BeforeEach
    public void setUp() {
        person = new Person(
                "Anna",
                "Yong",
                "Glimart street",
                "Ohio",
                123,
                "111-222-333",
                "anna@yong.com");
        list = new ArrayList<>();
        list.add(person);
    }

    @Test
    public void createNewPersonTest() {
        when(personRepository.create(person)).thenReturn(true);

        boolean result = personService.create(person);

        verify(personRepository).create(person);
        assertTrue(result);
    }

    @Test
    public void updatePersonTest() {
        when(personRepository.update(person)).thenReturn(true);

        boolean result = personService.update(person);

        verify(personRepository).update(person);
        assertTrue(result);
    }

    @Test
    public void deletePersonTest() {
        when(personRepository.delete(personId)).thenReturn(true);

        boolean result = personService.delete(personId);

        verify(personRepository).delete(personId);
        assertTrue(result);
    }

    @Test
    public void findAllTest() {
        when(personRepository.findAll()).thenReturn(Optional.of(list));

        Optional<List<Person>> result = personService.findAll();

        verify(personRepository).findAll();
        assertTrue(result.isPresent());
        assertTrue(result.get().contains(person));
    }

    @Test
    public void findByAddressTest() {
        String address = person.getAddress();
        when(personRepository.findByAddress(address)).thenReturn(Optional.of(list));

        Optional<List<Person>> result = personService.findByAddress(address);

        verify(personRepository).findByAddress(address);
        assertTrue(result.isPresent());
        assertTrue(result.get().contains(person));
    }

    @Test
    public void findByNameTest() {
        String firstName= person.getFirstName();
        String lastName = person.getLastName();
        when(personRepository.findByName(firstName, lastName)).thenReturn(Optional.of(list));

        Optional<List<Person>> result = personService.findByName(firstName, lastName);

        verify(personRepository).findByName(firstName, lastName);
        assertTrue(result.isPresent());
        assertTrue(result.get().contains(person));
    }
}
