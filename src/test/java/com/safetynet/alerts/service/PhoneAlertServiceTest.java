package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PhoneAlertServiceTest {
    @Mock
    private FireStationRepository fireStationRepository;
    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private PhoneAlertService phoneAlertService;

    @Test
    @DisplayName("Given there are households covered by station, return list of unique phone numbers ")
    public void findPhoneNumbers_shouldReturnListOfUniqueNumbers() {

        List<FireStation> fireStations = List.of(
                new FireStation("address1", 1),
                new FireStation("address2", 1)
        );

        List<Person> personsAddress1 = List.of(
                new Person("Marie", "Boyd", "address1", null, 123, "111-111", null),
                new Person("John", "Boyd", "address1", null, 123, "111-111", null));
        List<Person> personAddress2 = List.of(new Person("Tina", "Smith", "address2", null, 123, "222-222", null)
        );

        when(fireStationRepository.findByStationNumber(1)).thenReturn(Optional.of(fireStations));
        when(personRepository.findByAddress("address1")).thenReturn(Optional.of(personsAddress1));
        when(personRepository.findByAddress("address2")).thenReturn(Optional.of(personAddress2));

        Set<String> result = phoneAlertService.findPhoneNumbersByFireStation(1);

        verify(fireStationRepository, times(1)).findByStationNumber(1);
        verify(personRepository, times(1)).findByAddress("address1");
        verify(personRepository, times(1)).findByAddress("address2");
        assertEquals(2, result.size());


    }

}