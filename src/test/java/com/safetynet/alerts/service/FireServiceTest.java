package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonMedicalInfo;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FireServiceTest {

    @Mock
    private PersonService personService;
    @Mock
    private FireStationService fireStationService;
    @Mock
    private MedicalRecordService recordService;
    @InjectMocks
    private FireAndFloodService fireService;

    @Test
    public void findAllPersonsAtAddressTest() {
        List<Person> list = List.of(
                new Person("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com"),
                new Person("Jacob", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "drk@email.com")
        );

        when(personService.findByAddress("1509 Culver St")).thenReturn(Optional.of(list));

        List<Person> result = fireService.findAllPersonsAtAddress("1509 Culver St");

        verify(personService, times(1)).findByAddress("1509 Culver St");
        assertEquals(2, result.size());
    }

    @Test
    public void findMedicalRecordTest() {
        List<Person> list = List.of(
                new Person("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com"),
                new Person("Jacob", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "drk@email.com")
        );
        MedicalRecord record1 = new MedicalRecord("John", "Boyd", "03/06/1984", null, null);
        MedicalRecord record2 = new MedicalRecord("Jacob", "Boyd", "03/06/1984", null, null);

        when(recordService.findByName("John", "Boyd")).thenReturn(Optional.of(record1));
        when(recordService.findByName("Jacob", "Boyd")).thenReturn(Optional.of(record2));

        List<MedicalRecord> result = fireService.findMedicalRecord(list);

        verify(recordService, times(2)).findByName(anyString(), anyString());
        assertEquals(2, result.size());
    }

    @Test
    public void convertToRecordInfoListTest() {
        List<Person> personList = List.of(
                new Person("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com"),
                new Person("Jacob", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "drk@email.com")
        );
        List<MedicalRecord> records = List.of(
                new MedicalRecord("John", "Boyd", "03/06/1984", null, null),
                new MedicalRecord("Jacob", "Boyd", "03/06/1984", null, null)
        );

        List<PersonMedicalInfo> result = fireService.convertToRecordInfoList(personList, records);

        assertEquals(2, result.size());
    }

    @Test
    public void findFireStationTest() {

        FireStation fireStation = new FireStation("1509 Culver St", 1);
        when(fireStationService.findStationByAddress("1509 Culver St")).thenReturn(Optional.of(fireStation));

        int result = fireService.findFireStation("1509 Culver St");

        assertEquals(1, result);
    }
}
