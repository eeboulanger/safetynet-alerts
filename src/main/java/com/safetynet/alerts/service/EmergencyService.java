package com.safetynet.alerts.service;

import com.safetynet.alerts.model.*;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmergencyService implements IEmergencyService {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    FireStationRepository fireStationRepository;

    @Autowired
    MedicalRecordRepository medicalRecordRepository;


    public FireStationCoverage findPersonsCoveredByFireStation(int stationNumber) {
        List<Person> persons = findAllPersonsCoveredByStation(stationNumber);
        List<MedicalRecord> records = getRecordsForAllPersonsInList(persons);
        Map<String, Integer> count = countAdultsAndChildren(records);

        return new FireStationCoverage(getPersonInfoList(persons), count);
    }

    /**
     * Finds all persons covered by a fire station
     *
     * @param number is the fire station number
     * @return a list of persons
     */
    public List<Person> findAllPersonsCoveredByStation(int number) {
        Optional<List<FireStation>> optionalFireStations = fireStationRepository.findByStationNumber(number);

        Set<String> addresses = new HashSet<>();

        optionalFireStations.ifPresent(stations -> {
            for (FireStation station : stations) {
                addresses.add(station.getAddress());
            }
        });

        return addresses.stream()
                .map(address -> personRepository.findByAddress(address))
                .filter(Optional::isPresent)
                .flatMap(optionalList -> optionalList.get().stream())
                .collect(Collectors.toList());
    }

    public List<PersonInfo> getPersonInfoList(List<Person> personList) {
        return personList.stream()
                .map(person -> new PersonInfo(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()))
                .collect(Collectors.toList());
    }

    /**
     * Finds all medical records for persons in a list
     *
     * @param personList is the list of persons
     * @return a list of records
     */
    public List<MedicalRecord> getRecordsForAllPersonsInList(List<Person> personList) {
        return personList.stream()
                .map(person -> getMedicalRecord(person.getFirstName(), person.getLastName()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     * Finds a medical record for a person
     *
     * @param firstName of the person
     * @param lastName  of the person
     * @return the record
     */
    public Optional<MedicalRecord> getMedicalRecord(String firstName, String lastName) {
        return medicalRecordRepository.findByName(firstName, lastName);
    }

    /**
     * Counts the number of adults and children
     *
     * @param list of medical records containing birthdates
     * @return a map with adults and children as keys and the number of occurrences as value
     */
    public Map<String, Integer> countAdultsAndChildren(List<MedicalRecord> list) {
        Map<String, Integer> count = new HashMap<>();

        list.forEach(record -> {
            if (isAdult(record.getBirthdate())) {
                count.put("adults", count.getOrDefault("adults", 0) + 1);
            } else {
                count.put("children", count.getOrDefault("children", 0) + 1);
            }
        });
        return count;
    }

    /**
     * Checks if a birthdate is more than 18 years
     *
     * @param birthdate
     * @return true if birthdate more than 18 years ago
     */
    public boolean isAdult(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate dateOfBirth = LocalDate.parse(birthdate, formatter);

        LocalDate today = LocalDate.now();

        return Period.between(dateOfBirth, today).getYears() > 18;
    }
}
