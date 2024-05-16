package com.safetynet.alerts.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class PersonInfoDTOTest {

    @Test
    void testConstructorAndFieldAccess() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        int age = 30;
        List<String> medications = Arrays.asList("med1", "med2");
        List<String> allergies = Arrays.asList("pollen", "nuts");

        PersonInfoDTO personInfo = new PersonInfoDTO(
                firstName, lastName, email, age, medications, allergies
        );

        assertEquals(firstName, personInfo.getFirstName());
        assertEquals(lastName, personInfo.getLastName());
        assertEquals(email, personInfo.getEmail());
        assertEquals(age, personInfo.getAge());
        assertIterableEquals(medications, personInfo.getMedications());
        assertIterableEquals(allergies, personInfo.getAllergies());
    }

    @Test
    void testEquality() {
        PersonInfoDTO personInfo1 = new PersonInfoDTO(
                "John", "Doe", "john.doe@example.com", 30, List.of("med1"), List.of("pollen")
        );
        PersonInfoDTO personInfo2 = new PersonInfoDTO(
                "John", "Doe", "john.doe@example.com", 30, List.of("med1"), List.of("pollen")
        );

        assertEquals(personInfo1, personInfo2, "Two instances with the same values should be equal.");
    }

    @Test
    void testHashCode() {
        PersonInfoDTO personInfo1 = new PersonInfoDTO(
                "John", "Doe", "john.doe@example.com", 30, List.of("med1"), List.of("pollen")
        );
        PersonInfoDTO personInfo2 = new PersonInfoDTO(
                "John", "Doe", "john.doe@example.com", 30, List.of("med1"), List.of("pollen")
        );

        assertEquals(personInfo1.hashCode(), personInfo2.hashCode(), "Hash codes should be equal for equal objects.");
    }
}
