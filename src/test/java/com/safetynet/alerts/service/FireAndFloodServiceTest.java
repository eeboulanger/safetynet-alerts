package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.dto.FloodDTO;
import com.safetynet.alerts.dto.PersonMedicalInfo;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.AgeCalculator;
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
    private IPersonService personService;
    @Mock
    private IFireStationService fireStationService;
    @Mock
    private IMedicalRecordService recordService;
    @InjectMocks
    private FireAndFloodService fireService;

    @Nested
    public class DtoTests {
        private List<FireStation> fireStationList;
        private List<Person> personStation1;
        private List<Person> personStation2;
        private List<MedicalRecord> medicalRecordList;
        private FireStation station1;
        private FireStation station2;

        private List<PersonMedicalInfo> expectedResultFireStation1;
        private List<PersonMedicalInfo> expectedResultFireStation2;

        @BeforeEach
        public void setUp() {
            //Simulate two fire stations with different numbers and addresses
            fireStationList = List.of(
                    new FireStation("Elm street", 11),
                    new FireStation("Holmart street", 12)
            );
            //Persons covered by the first fire station
            personStation1 = List.of(new Person(
                            "Emma",
                            "Yong",
                            "Elm street",
                            "Denver",
                            123,
                            "1-22-333",
                            "emmas@mail.com"
                    ),
                    new Person(
                            "Camilla",
                            "Yong",
                            "Elm street",
                            "Denver",
                            123,
                            "1-22-333",
                            "emmas@mail.com"
                    ));
            //Persons covered by the second fire station
            personStation2 = List.of(new Person(
                            "Matthew",
                            "Loyd",
                            "Holmart street",
                            "Denver",
                            123,
                            "1-22-343",
                            "matthew@mail.com"
                    )
            );
            //Medical records for all covered persons
            medicalRecordList = List.of(new MedicalRecord(
                            "Emma",
                            "Yong",
                            "01/01/1950",
                            new ArrayList<>(),
                            new ArrayList<>()),
                    new MedicalRecord(
                            "Camilla",
                            "Yong",
                            "01/01/1990",
                            new ArrayList<>(),
                            new ArrayList<>()),
                    new MedicalRecord(
                            "Matthew",
                            "Loyd",
                            "01/01/1960",
                            new ArrayList<>(),
                            new ArrayList<>())
            );

            station1 = fireStationList.get(0);
            station2 = fireStationList.get(1);

            when(personService.findByAddress(station1.getAddress()))
                    .thenReturn(Optional.of(personStation1));
            when(recordService.findByName(personStation1.get(0).getFirstName(), personStation1.get(0).getLastName()))
                    .thenReturn(Optional.of(medicalRecordList.get(0)));
            when(recordService.findByName(personStation1.get(1).getFirstName(), personStation1.get(1).getLastName()))
                    .thenReturn(Optional.of(medicalRecordList.get(1)));


            expectedResultFireStation1 = List.of(new PersonMedicalInfo(
                            personStation1.get(0).getFirstName(),
                            personStation1.get(0).getLastName(),
                            personStation1.get(0).getPhone(),
                            AgeCalculator.calculateAge(medicalRecordList.get(0).getBirthdate(), "DD/mm/yyyy"),
                            medicalRecordList.get(0).getMedications(),
                            medicalRecordList.get(0).getAllergies()
                    ), new PersonMedicalInfo(
                            personStation1.get(1).getFirstName(),
                            personStation1.get(1).getLastName(),
                            personStation1.get(1).getPhone(),
                            AgeCalculator.calculateAge(medicalRecordList.get(1).getBirthdate(), "DD/mm/yyyy"),
                            medicalRecordList.get(1).getMedications(),
                            medicalRecordList.get(1).getAllergies()
                    )
            );
            expectedResultFireStation2 = List.of(new PersonMedicalInfo(
                            personStation2.get(0).getFirstName(),
                            personStation2.get(0).getLastName(),
                            personStation2.get(0).getPhone(),
                            AgeCalculator.calculateAge(medicalRecordList.get(2).getBirthdate(), "DD/mm/yyyy"),
                            medicalRecordList.get(2).getMedications(),
                            medicalRecordList.get(2).getAllergies()
                    )
            );
        }

        @Test
        public void findAllHouseHoldsCoveredByStationsTest() {
            when(fireStationService.findByStationNumber(station1.getStation()))
                    .thenReturn(Optional.of(List.of(station1)));
            when(fireStationService.findByStationNumber(station2.getStation()))
                    .thenReturn(Optional.of(List.of(station2)));
            when(personService.findByAddress(station2.getAddress()))
                    .thenReturn(Optional.of(personStation2));
            when(recordService.findByName(personStation2.get(0).getFirstName(), personStation2.get(0).getLastName()))
                    .thenReturn(Optional.of(medicalRecordList.get(2)));

            List<FloodDTO> result = fireService.findAllHouseHoldsCoveredByStations(List.of(station1.getStation(), station2.getStation()));

            verify(fireStationService).findByStationNumber(station1.getStation());
            verify(fireStationService).findByStationNumber(station2.getStation());
            verify(personService).findByAddress(station1.getAddress());
            verify(personService).findByAddress(station2.getAddress());
            verify(recordService).findByName(personStation1.get(0).getFirstName(), personStation1.get(0).getLastName());
            verify(recordService).findByName(personStation1.get(1).getFirstName(), personStation1.get(1).getLastName());
            verify(recordService).findByName(personStation2.get(0).getFirstName(), personStation2.get(0).getLastName());

            assertEquals(2, result.size());
            assertEquals(fireStationList.get(0).getAddress(), result.get(0).getAddress());
            assertEquals(fireStationList.get(0).getStation(), result.get(0).getFirestationNumber());
            assertEquals(expectedResultFireStation1, result.get(0).getMedicalInfoList());
            assertEquals(expectedResultFireStation2, result.get(1).getMedicalInfoList());
        }

        @Test
        public void findPersonsAndFireStationTest() {
            when(fireStationService.findStationByAddress(station1.getAddress())).thenReturn(Optional.of(station1));
            FireDTO result = fireService.findPersonsAndFireStation(station1.getAddress());

            verify(personService).findByAddress(station1.getAddress());
            verify(recordService).findByName(personStation1.get(0).getFirstName(), personStation1.get(0).getLastName());
            verify(recordService).findByName(personStation1.get(1).getFirstName(), personStation1.get(1).getLastName());
            assertNotNull(result);
            assertEquals(station1.getStation(), result.getFirestation());
            assertEquals(expectedResultFireStation1, result.getPersons());
        }
    }

    @Test
    public void findAllHouseHoldsCoveredByStationsFailsTest() {
        when(fireStationService.findByStationNumber(1)).thenReturn(Optional.empty());

        List<FloodDTO> result = fireService.findAllHouseHoldsCoveredByStations(List.of(1));

        verify(fireStationService).findByStationNumber(1);
        verify(personService, never()).findByAddress(anyString());
        verify(recordService, never()).findByName(anyString(), anyString());
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
                new MedicalRecord("John", "Boyd", "03/06/1984", List.of("100mg:Peratoxin"), List.of("peanuts")),
                new MedicalRecord("Jacob", "Boyd", "03/06/1984", List.of("100mg:dioxin"), List.of("pollen"))
        );

        List<PersonMedicalInfo> expectedResults = List.of(
                new PersonMedicalInfo(
                        records.get(0).getFirstName(),
                        records.get(0).getLastName(),
                        personList.get(0).getPhone(),
                        AgeCalculator.calculateAge(records.get(0).getBirthdate(), "DD/mm/yyyy"),
                        records.get(0).getMedications(),
                        records.get(0).getAllergies()
                ),
                new PersonMedicalInfo(
                        records.get(1).getFirstName(),
                        records.get(1).getLastName(),
                        personList.get(1).getPhone(),
                        AgeCalculator.calculateAge(records.get(1).getBirthdate(), "DD/mm/yyyy"),
                        records.get(1).getMedications(),
                        records.get(1).getAllergies()
                )
        );

        List<PersonMedicalInfo> result = fireService.convertToRecordInfoList(personList, records);

        assertEquals(2, result.size());
        assertEquals(expectedResults, result);
    }

    @Test
    public void findFireStationTest() {

        FireStation fireStation = new FireStation("1509 Culver St", 1);
        when(fireStationService.findStationByAddress("1509 Culver St")).thenReturn(Optional.of(fireStation));

        int result = fireService.findFireStation("1509 Culver St");

        assertEquals(1, result);
    }
}
