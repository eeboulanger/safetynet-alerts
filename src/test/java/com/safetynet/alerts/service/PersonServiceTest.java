package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

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

    private final Map<String, String> personId = new HashMap<>();

    @Test
    public void createNewPersonTest() {
        when(personRepository.create(person)).thenReturn(true);

        personService.create(person);

        verify(personRepository).create(person);
    }

    @Test
    public void updatePersonTest() {
        when(personRepository.update(person)).thenReturn(true);

        personService.update(person);

        verify(personRepository).update(person);
    }

    @Test
    public void deletePersonTest() {
        when(personRepository.delete(personId)).thenReturn(true);

        boolean result = personService.delete(personId);

        verify(personRepository).delete(personId);
        assertTrue(result);
    }
}
