package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
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
    private IPersonService personService;
    @Autowired
    private IFireStationService fireStationService;

    public Set<String> findPhoneNumbersByFireStation(int stationNumber) {

        return fireStationService.findByStationNumber(stationNumber).stream()
                .flatMap(Collection::stream)
                .flatMap(fireStation -> personService.findByAddress(fireStation.getAddress()).stream()
                        .flatMap(Collection::stream)
                        .map(Person::getPhone)
                )
                .collect(Collectors.toSet());
    }
}
