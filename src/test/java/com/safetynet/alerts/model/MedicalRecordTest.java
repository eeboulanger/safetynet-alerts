package com.safetynet.alerts.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

class MedicalRecordTest {

    @Test
    void testConstructorAndFieldAccess() {
        String firstName = "John";
        String lastName = "Doe";
        String birthdate = "01/01/1980";
        List<String> medications = Arrays.asList("med1", "med2");
        List<String> allergies = Arrays.asList("pollen", "nuts");

        MedicalRecord medicalRecord = new MedicalRecord(firstName, lastName, birthdate, medications, allergies);

        assertEquals(firstName, medicalRecord.getFirstName());
        assertEquals(lastName, medicalRecord.getLastName());
        assertEquals(birthdate, medicalRecord.getBirthdate());
        assertIterableEquals(medications, medicalRecord.getMedications());
        assertIterableEquals(allergies, medicalRecord.getAllergies());
    }

    @Test
    void testEquality() {
        MedicalRecord medicalRecord1 = new MedicalRecord("John", "Doe", "01/01/1980", List.of("med1"), List.of("pollen"));
        MedicalRecord medicalRecord2 = new MedicalRecord("John", "Doe", "01/01/1980", List.of("med1"), List.of("pollen"));

        assertEquals(medicalRecord1, medicalRecord2);
    }

    @Test
    void testHashCode() {
        MedicalRecord medicalRecord1 = new MedicalRecord("John", "Doe", "01/01/1980", List.of("med1"), List.of("pollen"));
        MedicalRecord medicalRecord2 = new MedicalRecord("John", "Doe", "01/01/1980", List.of("med1"), List.of("pollen"));

        assertEquals(medicalRecord1.hashCode(), medicalRecord2.hashCode(), "Hash codes should be equal for equal objects.");
    }
}
