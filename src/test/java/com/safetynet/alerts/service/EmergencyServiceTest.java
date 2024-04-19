package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmergencyServiceTest {

    @Mock
    FireStationRepository fireStationRepository;

    @Mock
    Optional<List<FireStation>> mockedStations;

    @InjectMocks
    EmergencyService service;

    @Test
    public void getListOfPersonsCoveredByStation() {
        when(fireStationRepository.findByStationNumber(1)).thenReturn(mockedStations);

        List<Person> persons = service.findAllPersonsCoveredByStation(1);

        verify(fireStationRepository).findByStationNumber(1);
        assertEquals(0, persons.size());
    }
}
