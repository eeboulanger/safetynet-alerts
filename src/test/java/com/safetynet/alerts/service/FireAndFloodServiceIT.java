package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.dto.FloodDTO;
import com.safetynet.alerts.dto.PersonMedicalInfo;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FireAndFloodServiceIT {

    @Autowired
    private FireAndFloodService fireService;

    @Test
    @DisplayName("Given there are persons covered when entering address then return list of persons and fire station number")
    public void findPersonsAndFireStationByAddress_shouldReturnListOfPersonsAndFireStationNumber() {
        List<PersonMedicalInfo> persons = List.of(
                new PersonMedicalInfo("Zach", "Zemicks", "841-874-7512", 7, new ArrayList<>(), new ArrayList<>()),
                new PersonMedicalInfo("Warren", "Zemicks", "841-874-7512", 39, new ArrayList<>(), new ArrayList<>()),
                new PersonMedicalInfo("Sophia", "Zemicks", "841-874-7878", 36,
                        List.of("aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg", "terazine:500mg"),
                        List.of("peanut", "shellfish", "aznol"))
        );

        FireDTO result = fireService.findPersonsAndFireStation("892 Downing Ct");
        List<String> firstNames = result.getPersons().stream()
                .map(PersonMedicalInfo::getFirstName)
                .toList();

        assertEquals(3, result.getPersons().size());
        assertTrue(result.getPersons().containsAll(persons));
    }

    @Test
    @DisplayName("Find persons covered by stations should return list of fire staiton numbers, address and list of medical information of persons covered ")
    public void findAllHouseHoldsCoveredByStations(){
        List<Integer> stations = List.of(1,2);

        List<FloodDTO> result = fireService.findAllHouseHoldsCoveredByStations(stations);

        assertNotNull(result);
        assertEquals(6, result.size());
    }

    @Test
    @DisplayName("Given there are persons when entering address, then return list")
    public void findAllPersonsAtAddressTest() {
        List<String> expectedNames = List.of("John", "Jacob", "Tenley", "Roger", "Felicia");

        List<Person> result = fireService.findAllPersonsAtAddress("1509 Culver St");
        List<String> firstNames = result.stream()
                .map(Person::getFirstName)
                .toList();

        assertEquals(5, result.size());
        assertTrue(firstNames.containsAll(expectedNames));
    }

    @Test
    @DisplayName("Given there are records when searching on person, then return list")
    public void findMedicalRecordTest() {
        List<Person> persons = fireService.findAllPersonsAtAddress("1509 Culver St");
        List<String> expectedBirthdates = List.of("03/06/1984", "03/06/1989", "02/18/2012", "09/06/2017", "01/08/1986");

        List<MedicalRecord> result = fireService.findMedicalRecord(persons);
        List<String> birthdates = result.stream()
                .map(MedicalRecord::getBirthdate)
                .toList();

        assertEquals(5, result.size());
        assertTrue(birthdates.containsAll(expectedBirthdates));
    }

    @Test
    @DisplayName("Given there are persons and records, then return list")
    public void convertToRecordInfoList() {
        List<String> expectedNames = List.of("John", "Jacob", "Tenley", "Roger", "Felicia");

        List<Person> persons = fireService.findAllPersonsAtAddress("1509 Culver St");
        List<MedicalRecord> records = fireService.findMedicalRecord(persons);
        List<PersonMedicalInfo> result = fireService.convertToRecordInfoList(persons, records);

        List<String> firstNames = result.stream()
                .map(PersonMedicalInfo::getFirstName)
                .toList();

        assertEquals(5, result.size());
        assertTrue(firstNames.containsAll(expectedNames));
    }

    @Test
    @DisplayName("Given there's a firestation when entering address then return fire station")
    public void findFireStationNumberTest() {

        int result = fireService.findFireStation("489 Manchester St");

        assertEquals(4, result);
    }
}
