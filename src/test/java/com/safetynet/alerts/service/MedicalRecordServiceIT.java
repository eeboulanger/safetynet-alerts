package com.safetynet.alerts.service;

import com.safetynet.alerts.DataPrepareService;
import com.safetynet.alerts.model.MedicalRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MedicalRecordServiceIT {
    @Autowired
    private MedicalRecordService recordService;
    private DataPrepareService prepareService;

    @BeforeEach
    public void setUp() {
        prepareService = new DataPrepareService();
    }

    @AfterEach
    public void tearDown() throws IOException {
        prepareService.resetData();
    }

    @Test
    public void createMedicalRecordTest() {
        MedicalRecord record = new MedicalRecord(
                "First name",
                "last name",
                "",
                new ArrayList<>(),
                new ArrayList<>()
        );

        boolean isCreated = recordService.create(record);
        assertTrue(isCreated);
    }

    @Test
    public void updateMedicalRecordTest() {
        MedicalRecord record = prepareService.getMedicalRecord(0);
        record.setBirthdate("new birthdate");

        boolean isUpdated = recordService.update(record);
        assertTrue(isUpdated);
    }

    @Test
    public void deleteMedicalRecordTest() {
        MedicalRecord record = prepareService.getMedicalRecord(1);

        boolean isDeleted = recordService.delete(Map.of(
                        "firstname", record.getFirstName(),
                        "lastname", record.getLastName()
                )
        );
        assertTrue(isDeleted);
    }

    @Test
    public void findAllTest() {
        Optional<List<MedicalRecord>> optional = recordService.findAll();
        List<MedicalRecord> result = new ArrayList<>();

        if (optional.isPresent()) {
            result = optional.get();
        }

        assertFalse(result.isEmpty());
        assertEquals(prepareService.getMedicalRecordsList().size(), result.size());
    }

    @Test
    public void findByNameTest(){
        MedicalRecord record = prepareService.getMedicalRecord(0);

        Optional<MedicalRecord> optional = recordService.findByName(record.getFirstName(), record.getLastName());
        MedicalRecord result = new MedicalRecord();

        if(optional.isPresent()){
        result = optional.get();
        }

        assertNotNull(result);
        assertEquals(record.getFirstName(), result.getFirstName());
        assertEquals(record.getLastName(), result.getLastName());
    }
}
