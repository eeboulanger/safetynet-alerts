package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;

@Service
public class FireStationService {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    FireStationRepository fireStationRepository;

    public List<Person> findAllPersonsCoveredByStation(int number) {
        Optional<List<FireStation>> optionalFireStations = fireStationRepository.findByStationNumber(number);
        Set<String> addresses = new HashSet<>();

        optionalFireStations.ifPresent(stations -> {
            for (FireStation station : stations) {
                addresses.add(station.getAddress());
            }
        });

        List<Person> coveredPersons = new ArrayList<>();
        for (String address : addresses) {
            Optional<List<Person>> optional = personRepository.findByAddress(address);
            optional.ifPresent(coveredPersons :: addAll);
        }

        return coveredPersons;
    }
}
