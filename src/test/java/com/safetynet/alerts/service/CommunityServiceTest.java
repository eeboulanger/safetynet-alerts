package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommunityServiceTest {

    @Mock
    private PersonService personService;
    @Mock
    private MedicalRecordService recordService;

    @InjectMocks
    private CommunityService communityService;


    @Test
    @DisplayName("Given there are persons when entering first and last name, then return list")
    public void getAllPersonsByName() {
        Person person = new Person("John", "Boyd",
                "1509 Culver St", "Culver", 97451,
                "841-874-6512", "jaboyd@email.com");
        MedicalRecord record = new MedicalRecord(
                "John", "Boyd", "03/06/1984",
                List.of("aznol:350mg", "hydrapermazol:100mg"),
                List.of("nillacilan")
        );

        when(personService.findByName("John", "Boyd")).thenReturn(Optional.of(List.of(person)));
        when(recordService.findByName("John", "Boyd")).thenReturn(Optional.of(record));

        List<PersonInfoDTO> result = communityService.getAllPersonsByName("John", "Boyd");

        verify(personService, times(1)).findByName("John", "Boyd");
        verify(recordService, times(1)).findByName("John", "Boyd");
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Given there are no persons when entering first and last name, then return empty list")
    public void getAllPersonsByName_whenNoPersons_thenReturnEmptyList() {

        when(personService.findByName("John", "Boyd")).thenReturn(Optional.empty());

        List<PersonInfoDTO> result = communityService.getAllPersonsByName("John", "Boyd");

        verify(personService, times(1)).findByName("John", "Boyd");
        verify(recordService, never()).findByName("John", "Boyd");
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
