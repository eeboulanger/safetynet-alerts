package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Primary
public class PhoneAlertService implements IPhoneAlertService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private FireStationRepository fireStationRepository;

    public Set<String> findPhoneNumbersByFireStation(int stationNumber) {

        return fireStationRepository.findByStationNumber(stationNumber).stream()
                .flatMap(Collection::stream)
                .flatMap(fireStation -> personRepository.findByAddress(fireStation.getAddress()).stream()
                        .flatMap(Collection::stream)
                        .map(Person::getPhone)
                )
                .collect(Collectors.toSet());
    }
}
