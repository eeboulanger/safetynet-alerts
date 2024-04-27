package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void getAllEmailsTest() {
        List<Person> list = List.of(
                new Person("Clive",
                        "Ferguson",
                        "748 Townings Dr",
                        "Culver",
                        97451,
                        "841-874-6741",
                        "clivfd@ymail.com"),
                new Person("Eric",
                        "Cadigan",
                        "951 LoneTree Rd",
                        "Culver",
                        97451,
                        "841-874-7458",
                        "gramps@email.com"
                )
        );

        when(personRepository.findAll()).thenReturn(Optional.of(list));

        Set<String> result = emailService.getAllEmails("Culver");

        verify(personRepository, times(1)).findAll();
        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of("gramps@email.com", "clivfd@ymail.com")));
    }

    @Test
    @DisplayName("No persons in city should return empty list")
    public void getAllEmailsWhenNoPersonsInCityTest() {
        when(personRepository.findAll()).thenReturn(Optional.empty());

        Set<String> result = emailService.getAllEmails("No city");

        verify(personRepository, times(1)).findAll();
        assertTrue(result.isEmpty());
    }
}
