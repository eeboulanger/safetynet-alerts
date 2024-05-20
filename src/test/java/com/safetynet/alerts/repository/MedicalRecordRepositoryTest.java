package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MedicalRecordRepositoryTest {

    private final MedicalRecordRepository repository = new MedicalRecordRepository();
    private MedicalRecord medicalRecord;

    @BeforeEach
    public void setUp() {
        medicalRecord = new MedicalRecord(
                "Emma",
                "Tower",
                "01/01/1950",
                List.of("200mg:Tredox"),
                List.of("peanuts", "shellfish")
        );

        repository.saveAll(new ArrayList<>(List.of(medicalRecord)));
    }

    @Test
    public void createTest() {
        MedicalRecord record = new MedicalRecord(
                "No name",
                "Test",
                "birthdate",
                List.of("meds"),
                List.of("allergies")
        );

        boolean isCreated = repository.create(record);

        assertTrue(isCreated);
    }

    @Test
    public void updateTest() {
        medicalRecord.setAllergies(List.of("peanuts"));
        boolean isUpdated = repository.update(medicalRecord);

        assertTrue(isUpdated);
    }

    @Test
    public void deletedTest() {
        Map<String, String> map = Map.of(
                "firstName", medicalRecord.getFirstName(),
                "lastName", medicalRecord.getLastName()
        );
        boolean isDeleted = repository.delete(map);

        assertTrue(isDeleted);
    }

    @Test
    public void findAllTest() {
        Optional<List<MedicalRecord>> optional = repository.findAll();

        assertTrue(optional.isPresent());
        assertTrue(optional.get().contains(medicalRecord));
    }

    @Test
    public void findByNameTest() {
        Optional<MedicalRecord> result = repository.findByName(medicalRecord.getFirstName(), medicalRecord.getLastName());

        assertTrue(result.isPresent());
        assertEquals(result.get(), medicalRecord);
    }

    @Test
    @DisplayName("Create when medical record already exists with the given name should fail")
    public void createFailsTest() {
        boolean isCreated = repository.create(medicalRecord);

        assertFalse(isCreated);
    }

    @Test
    @DisplayName("Update when medical record doesn't exist with the given name should fail")
    public void updateFailsTest() {
        MedicalRecord record = new MedicalRecord(
                "No name",
                "Test",
                "birthdate",
                List.of("meds"),
                List.of("allergies")
        );
        boolean isUpdated = repository.update(record);

        assertFalse(isUpdated);
    }

    @Test
    @DisplayName("Delete when medical record doesn't exist with the given name should fail")
    public void deletedFailsTest() {
        Map<String, String> map = Map.of(
                "firstName", "No such name",
                "lastName", "Test"
        );

        boolean isDeleted = repository.delete(map);

        assertFalse(isDeleted);
    }

    @Test
    @DisplayName("Find all when no result should return empty list")
    public void findAllFailsTest() {
        repository.saveAll(new ArrayList<>());
        Optional<List<MedicalRecord>> optional = repository.findAll();

        assertTrue(optional.isPresent());
        assertTrue(optional.get().isEmpty());
    }

    @Test
    @DisplayName("Find by name when no result should return empty optional")
    public void findByNameFailsTest() {
        Optional<MedicalRecord> result = repository.findByName("No existing name", "test");

        assertTrue(result.isEmpty());
    }
}




