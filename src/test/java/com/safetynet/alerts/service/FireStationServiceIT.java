package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FireStationServiceIT {

    @Autowired
    FireStationService service;

    @Test
    public void getPersonsCoveredByFireStationTest() {

        List<Person> personList = service.findAllPersonsCoveredByStation(1);

        assertEquals(6, personList.size());
    }
}
