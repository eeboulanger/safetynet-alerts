package com.safetynet.alerts.service;

import com.safetynet.alerts.config.DataInitializer;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MedicalRecordServiceIT {
    @Autowired
    private MedicalRecordService recordService;
    @Autowired
    private MedicalRecordRepository repository;
    private MedicalRecord record;
    @Autowired
    private DataInitializer dataInitializer;

    @BeforeEach
    public void setUp() {
        dataInitializer.run();
        record = repository.findAll().isPresent() ? repository.findAll().get().get(0) : new MedicalRecord();
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
        boolean isUpdated = recordService.update(record);
        assertTrue(isUpdated);
    }

    @Test
    public void deleteMedicalRecordTest() {
        boolean isDeleted = recordService.delete(Map.of(
                        "firstName", record.getFirstName(),
                        "lastName", record.getLastName()
                )
        );
        assertTrue(isDeleted);
    }

    @Test
    public void findAllTest() {
        Optional<List<MedicalRecord>> result = recordService.findAll();

        assertTrue(result.isPresent());
        assertEquals(23, result.get().size());
    }

    @Test
    public void findByNameTest() {
        Optional<MedicalRecord> result = recordService.findByName(record.getFirstName(), record.getLastName());
        assertTrue(result.isPresent());
        assertEquals(record.getFirstName(), result.get().getFirstName());
        assertEquals(record.getLastName(), result.get().getLastName());
    }
}
