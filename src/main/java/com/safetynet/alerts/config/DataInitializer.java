package com.safetynet.alerts.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataContainer;
import com.safetynet.alerts.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private IPersonRepository personRepository;
    @Autowired
    private IFireStationRepository fireStationRepository;
    @Autowired
    private IMedicalRecordRepository medicalRecordRepository;
    @Autowired
    private ObjectMapper mapper;
    private static final String JSON_DATA_PATH = "./data/data.json";
    private static final Logger logger = LogManager.getLogger(DataInitializer.class);

    @Override
    public void run(String... args) {
        File jsonFile = new File(JSON_DATA_PATH);
        if (!jsonFile.exists()) {
            logger.error("File " + JSON_DATA_PATH + " doesn't exist");
            return;
        }
        try {
            logger.debug("Reading data from file: " + JSON_DATA_PATH);
            DataContainer data = mapper.readValue(jsonFile, DataContainer.class);

            personRepository.saveAll(data.getPersons());
            fireStationRepository.saveAll(data.getFirestations());
            medicalRecordRepository.saveAll(data.getMedicalrecords());
        } catch (Exception e) {
            logger.error("Error reading data from file: " + e);
        }
    }
}
