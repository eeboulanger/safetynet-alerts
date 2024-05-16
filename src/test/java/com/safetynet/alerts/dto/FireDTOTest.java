package com.safetynet.alerts.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class FireDTOTest {

    @Test
    void testConstructorAndFieldAccess() {
        List<PersonMedicalInfo> persons = Arrays.asList(
                new PersonMedicalInfo("John", "Doe", "123-456-7890", 35, List.of("med1"), List.of("allergy1")),
                new PersonMedicalInfo("Jane", "Doe", "987-654-3210", 34, List.of("med2"), List.of("allergy2"))
        );
        int firestation = 101;

        FireDTO fireDTO = new FireDTO(persons, firestation);

        assertEquals(persons, fireDTO.getPersons());
        assertEquals(firestation, fireDTO.getFirestation());
    }

    @Test
    void testEquality() {
        List<PersonMedicalInfo> persons = List.of(
                new PersonMedicalInfo("John", "Doe", "123-456-7890", 35, List.of("med1"), List.of("allergy1"))
        );
        FireDTO fireDTO1 = new FireDTO(persons, 101);
        FireDTO fireDTO2 = new FireDTO(persons, 101);

        assertEquals(fireDTO1, fireDTO2, "Two instances with the same values should be equal.");
    }

    @Test
    void testHashCode() {
        List<PersonMedicalInfo> persons = List.of(
                new PersonMedicalInfo("John", "Doe", "123-456-7890", 35, List.of("med1"), List.of("allergy1"))
        );
        FireDTO fireDTO1 = new FireDTO(persons, 101);
        FireDTO fireDTO2 = new FireDTO(persons, 101);

        assertEquals(fireDTO1.hashCode(), fireDTO2.hashCode(), "Hash codes should be equal for equal objects.");
    }
}
