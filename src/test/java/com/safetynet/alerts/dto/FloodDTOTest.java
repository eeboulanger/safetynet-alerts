package com.safetynet.alerts.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class FloodDTOTest {

    @Test
    void testConstructorAndFieldAccess() {
        int firestationNumber = 5;
        String address = "123 Elm Street";
        List<PersonMedicalInfo> medicalInfoList = Arrays.asList(
                new PersonMedicalInfo("John", "Doe", "555-1234", 30, List.of("med1"), List.of("allergy1")),
                new PersonMedicalInfo("Jane", "Doe", "555-5678", 25, List.of("med2"), List.of("allergy2"))
        );

        FloodDTO floodDTO = new FloodDTO(firestationNumber, address, medicalInfoList);

        assertEquals(firestationNumber, floodDTO.getFirestationNumber());
        assertEquals(address, floodDTO.getAddress());
        assertIterableEquals(medicalInfoList, floodDTO.getMedicalInfoList());
    }

    @Test
    void testEquality() {
        List<PersonMedicalInfo> medicalInfoList = List.of(
                new PersonMedicalInfo("John", "Doe", "555-1234", 30, List.of("med1"), List.of("allergy1"))
        );
        FloodDTO floodDTO1 = new FloodDTO(5, "123 Elm Street", medicalInfoList);
        FloodDTO floodDTO2 = new FloodDTO(5, "123 Elm Street", medicalInfoList);

        assertEquals(floodDTO1, floodDTO2, "Two instances with the same values should be equal.");
    }

    @Test
    void testHashCode() {
        List<PersonMedicalInfo> medicalInfoList = List.of(
                new PersonMedicalInfo("John", "Doe", "555-1234", 30, List.of("med1"), List.of("allergy1"))
        );
        FloodDTO floodDTO1 = new FloodDTO(5, "123 Elm Street", medicalInfoList);
        FloodDTO floodDTO2 = new FloodDTO(5, "123 Elm Street", medicalInfoList);

        // Act & Assert
        assertEquals(floodDTO1.hashCode(), floodDTO2.hashCode(), "Hash codes should be equal for equal objects.");
    }
}
