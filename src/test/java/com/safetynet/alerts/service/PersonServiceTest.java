package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private com.safetynet.alerts.service.PersonService personService;

    private final Person person = new Person();
    private final List<Person> list = List.of(
            new Person("Firstname", "Lastname", "address", "city", 123, "phone", "email")
    );

    private final Map<String, String> personId = new HashMap<>();

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
        assertEquals(1, result.get().size());
    }

    @Test
    public void findByAddressTest() {
        when(personRepository.findByAddress("address")).thenReturn(Optional.of(list));

        Optional<List<Person>> result = personService.findByAddress("address");

        verify(personRepository).findByAddress("address");
        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
    }

    @Test
    public void findByNameTest() {
        when(personRepository.findByName("Firstname", "Lastname")).thenReturn(Optional.of(list));

        Optional<List<Person>> result = personService.findByName("Firstname", "Lastname");

        verify(personRepository).findByName("Firstname", "Lastname");
        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
    }
}
