package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private com.safetynet.alerts.service.PersonService personService;

    private final Person person = new Person();

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
        when(personRepository.delete(person)).thenReturn(true);

        personService.delete(person);

        verify(personRepository).delete(person);
    }
}
