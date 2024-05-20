package com.safetynet.alerts.config;

import com.safetynet.alerts.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DataInitializerIT {
    @Autowired
    private DataInitializer dataInitializer;
    @Autowired
    private IPersonRepository personRepository;
    @Autowired
    private IFireStationRepository fireStationRepository;
    @Autowired
    private IMedicalRecordRepository medicalRecordRepository;
    @Test
    public void initializeDataTest() {
        dataInitializer.run();

        assertTrue(personRepository.findAll().isPresent());
        assertEquals(23, personRepository.findAll().get().size());
        assertTrue(fireStationRepository.findAll().isPresent());
        assertEquals(13, fireStationRepository.findAll().get().size());
        assertTrue(medicalRecordRepository.findAll().isPresent());
        assertEquals(23, medicalRecordRepository.findAll().get().size());
    }
}
