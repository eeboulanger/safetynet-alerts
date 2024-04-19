package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MedicalRecordRepositoryTest {

    private final MedicalRecordRepository repository = new MedicalRecordRepository();

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
    public void findByNameTest(){
        Optional<MedicalRecord> optional = repository.findByName("Shawna", "Stelzer");
        MedicalRecord record = null;
        if(optional.isPresent()) {
             record = optional.get();
        }

        assertNotNull(record);
        assertEquals("Shawna", record.getFirstName());
        assertEquals("07/08/1980", record.getBirthdate());
    }
}
