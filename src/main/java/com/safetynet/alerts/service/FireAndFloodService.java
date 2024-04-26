package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireInfo;
import com.safetynet.alerts.dto.FloodInfo;
import com.safetynet.alerts.dto.PersonMedicalInfo;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.safetynet.alerts.util.AgeCalculator.calculateAge;

@Service
public class FireAndFloodService implements IFireAndFloodService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private FireStationRepository fireStationRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Override
    public List<FloodInfo> findAllHouseHoldsCoveredByStations(List<Integer> fireStationNumbers) {

        List<FloodInfo> floodInfoList = new ArrayList<>();

        for (int stationNumber : fireStationNumbers) {
            Optional<List<FireStation>> optional = fireStationRepository.findByStationNumber(stationNumber);

            //A fire station can have multiple addresses so get all of them
            if (optional.isPresent()) {

                Set<String> addresses = optional.get().stream()
                        .map(FireStation::getAddress)
                        .collect(Collectors.toSet());

                //For each address get persons covered
                for (String address : addresses) {
                    List<Person> personList = findAllPersonsAtAddress(address);
                    List<MedicalRecord> recordList = findMedicalRecord(personList);
                    List<PersonMedicalInfo> medicalInfoList = convertToRecordInfoList(personList, recordList);

                    FloodInfo floodInfo = new FloodInfo(stationNumber, address, medicalInfoList);
                    floodInfoList.add(floodInfo);
                }
            }
        }
        return floodInfoList;
    }


    @Override
    public FireInfo findPersonsAndFireStation(String address) {

        List<Person> persons = findAllPersonsAtAddress(address);
        List<MedicalRecord> records = findMedicalRecord(persons);
        List<PersonMedicalInfo> personMedicalInfoList = convertToRecordInfoList(persons, records);

        int fireStationNumber = findFireStation(address);

        return new FireInfo(personMedicalInfoList, fireStationNumber);
    }

    /**
     * Finds medical information for each person in a given list
     *
     * @param persons is the list of persons used for searching
     * @return a list of medical records
     */
    public List<MedicalRecord> findMedicalRecord(List<Person> persons) {
        return persons.stream()
                .map(person -> medicalRecordRepository.findByName(person.getFirstName(), person.getLastName()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     * Finds all persons at a given address
     *
     * @param address used for searching
     * @return a list of persons
     */
    public List<Person> findAllPersonsAtAddress(String address) {
        return personRepository.findByAddress(address).stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * Converts information of persons into a list of record info containing firstname, lastname, phonenumber, age, list of medications, list of allergies
     *
     * @param persons list of persons containing phonenumber and name
     * @param records list of persons containing birthdate, medications and allergies
     * @return a list of record info
     */
    public List<PersonMedicalInfo> convertToRecordInfoList(List<Person> persons, List<MedicalRecord> records) {
        return records.stream()
                .map(record -> new PersonMedicalInfo(
                        record.getFirstName(),
                        record.getLastName(),
                        persons.stream()
                                .filter(person -> person.getLastName().equals(record.getLastName())
                                        && person.getFirstName().equals(record.getFirstName()))
                                .map(Person::getPhone)
                                .findFirst()
                                .orElse(""),
                        calculateAge(record.getBirthdate(), "DD/mm/yyyy"),
                        record.getMedications(),
                        record.getAllergies()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Find fire station number by address
     *
     * @param address used for search
     * @return number
     */
    public int findFireStation(String address) {
        return fireStationRepository.findStationByAddress(address)
                .map(FireStation::getStation)
                .orElse(0);
    }
}
