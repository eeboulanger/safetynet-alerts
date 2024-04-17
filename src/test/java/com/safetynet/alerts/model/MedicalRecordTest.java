package com.safetynet.alerts.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MedicalRecordTest {

    @Test
    public void getMedicalRecordInfoTest() {

        List<String> medications = Arrays.asList("aznol:350mg", "hydrapermazol:100mg");
        List<String> allergies = Arrays.asList("nillacilan");
        MedicalRecord record = new MedicalRecord("Paul", "Bloom", "03/06/1984", medications, allergies);

        assertEquals("Paul", record.getFirstName());
        assertEquals("Bloom", record.getLastName());
        assertEquals("03/06/1984", record.getBirthdate());
        assertEquals(medications, record.getMedications());
        assertEquals(allergies, record.getAllergies());
    }
}
