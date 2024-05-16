package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.dto.FloodDTO;
import com.safetynet.alerts.dto.PersonMedicalInfo;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FireAndFloodServiceTest {

    @Mock
    private PersonService personService;
    @Mock
    private FireStationService fireStationService;
    @Mock
    private MedicalRecordService recordService;
    @InjectMocks
    private FireAndFloodService fireService;

    @Nested
    public class DtoTests {
        private List<FireStation> fireStationList;

        @BeforeEach
        public void setUp() {
            fireStationList = List.of(
                    new FireStation("address", 1)
            );
            List<Person> personList = List.of(new Person(
                            "Test firstname",
                            "Test lastname",
                            "address",
                            "city",
                            123,
                            "phone",
                            "mail"
                    )
            );
            MedicalRecord medicalRecord = new MedicalRecord(
                    "Test firstname",
                    "Test lastname",
                    "01/01/1950",
                    new ArrayList<>(),
                    new ArrayList<>()

            );

            when(personService.findByAddress("address")).thenReturn(Optional.of(personList));
            when(recordService.findByName("Test firstname", "Test lastname")).thenReturn(Optional.of(medicalRecord));

        }

        @Test
        public void findAllHouseHoldsCoveredByStationsTest() {
            when(fireStationService.findByStationNumber(1)).thenReturn(Optional.of(fireStationList));

            List<FloodDTO> result = fireService.findAllHouseHoldsCoveredByStations(List.of(1));

            verify(fireStationService).findByStationNumber(1);
            verify(personService).findByAddress("address");
            verify(recordService).findByName("Test firstname", "Test lastname");
            assertEquals(1, result.size());
            assertEquals(fireStationList.get(0).getAddress(), result.get(0).getAddress());
            assertEquals(fireStationList.get(0).getStation(), result.get(0).getFirestationNumber());
            assertEquals("Test firstname", result.get(0).getMedicalInfoList().get(0).getFirstName());
        }

        @Test
        public void findPersonsAndFireStationTest() {
            FireStation station = fireStationList.get(0);
            when(fireStationService.findStationByAddress("address")).thenReturn(Optional.of(station));

            FireDTO result = fireService.findPersonsAndFireStation("address");

            verify(personService).findByAddress("address");
            verify(recordService).findByName("Test firstname", "Test lastname");
            verify(fireStationService).findStationByAddress("address");
            assertEquals(1, result.getFirestation());
        }
    }

    @Test
    public void findAllHouseHoldsCoveredByStationsFailsTest() {
        when(fireStationService.findByStationNumber(1)).thenReturn(Optional.empty());

        List<FloodDTO> result = fireService.findAllHouseHoldsCoveredByStations(List.of(1));

        verify(fireStationService).findByStationNumber(1);
        verify(personService, never()).findByAddress("address");
        verify(recordService, never()).findByName("Test firstname", "Test lastname");
        assertEquals(0, result.size());
    }

    @Test
    public void findPersonsAndFireStationFailsTest() {
        when(personService.findByAddress("address")).thenReturn(Optional.empty());
        when(fireStationService.findStationByAddress("address")).thenReturn(Optional.empty());

        FireDTO result = fireService.findPersonsAndFireStation("address");

        verify(personService).findByAddress("address");
        verify(fireStationService).findStationByAddress("address");
        assertEquals(0, result.getPersons().size());
        assertEquals(0, result.getFirestation());
    }

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
