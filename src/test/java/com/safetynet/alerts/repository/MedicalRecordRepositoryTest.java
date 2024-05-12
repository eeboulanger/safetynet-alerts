package com.safetynet.alerts.repository;

import com.safetynet.alerts.DataPrepareService;
import com.safetynet.alerts.model.MedicalRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MedicalRecordRepositoryTest {

    private final MedicalRecordRepository repository = new MedicalRecordRepository();
    private DataPrepareService dataPrepareService;

    @BeforeEach
    public void setUp() {
        dataPrepareService = new DataPrepareService();
    }

    @AfterEach
    public void tearDown() throws IOException {
        dataPrepareService.resetData();
    }

    @Test
    public void createMedicalRecordTest() {
        MedicalRecord record = new MedicalRecord(
                "First name",
                "last name",
                "01/01/1999",
                new ArrayList<>(),
                new ArrayList<>()
        );

        boolean isCreated = repository.create(record);
        assertTrue(isCreated);
    }

    @Test
    public void updateMedicalRecordTest() {
        MedicalRecord record = dataPrepareService.getMedicalRecord(0);
        record.setAllergies(List.of(
                "allergy1", "allergy2"
        ));
        boolean isUpdated = repository.update(record);
        assertTrue(isUpdated);
    }

    @Test
    public void deleteMedicalRecordTest() {
        MedicalRecord record = dataPrepareService.getMedicalRecord(1);

        boolean isDeleted = repository.delete(Map.of(
                        "firstname", record.getFirstName(),
                        "lastname", record.getLastName()
                )
        );
        assertTrue(isDeleted);
    }

    @Test
    public void getAllPersonsFromJsonTest() {
        Optional<List<MedicalRecord>> optional = repository.findAll();
        List<MedicalRecord> records = new ArrayList<>();
        if (optional.isPresent()) {
            records = optional.get();
        }
        assertFalse(records.isEmpty());
        assertEquals("John", records.get(0).getFirstName());
        assertEquals("Boyd", records.get(0).getLastName());
        assertEquals("03/06/1984", records.get(0).getBirthdate());
        assertEquals("aznol:350mg", records.get(0).getMedications().get(0));
        assertEquals("nillacilan", records.get(0).getAllergies().get(0));
    }

    @Test
    public void findByNameTest() {
        Optional<MedicalRecord> optional = repository.findByName("Shawna", "Stelzer");
        MedicalRecord record = null;
        if (optional.isPresent()) {
            record = optional.get();
        }

        assertNotNull(record);
        assertEquals("Shawna", record.getFirstName());
        assertEquals("07/08/1980", record.getBirthdate());
    }
}
