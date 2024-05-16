package com.safetynet.alerts.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class PersonMedicalInfoTest {

    @Test
    void testConstructorAndFieldAccess() {
        String firstName = "John";
        String lastName = "Doe";
        String phoneNumber = "123-456-7890";
        int age = 30;
        List<String> medications = Arrays.asList("med1", "med2");
        List<String> allergies = Arrays.asList("pollen", "nuts");

        PersonMedicalInfo personInfo = new PersonMedicalInfo(
                firstName, lastName, phoneNumber, age, medications, allergies
        );

        assertEquals(firstName, personInfo.getFirstName());
        assertEquals(lastName, personInfo.getLastName());
        assertEquals(phoneNumber, personInfo.getPhoneNumber());
        assertEquals(age, personInfo.getAge());
        assertIterableEquals(medications, personInfo.getMedications());
        assertIterableEquals(allergies, personInfo.getAllergies());
    }

    @Test
    void testEquality() {
        PersonMedicalInfo personInfo1 = new PersonMedicalInfo(
                "John", "Doe", "123-456-7890", 30, List.of("med1"), List.of("pollen")
        );
        PersonMedicalInfo personInfo2 = new PersonMedicalInfo(
                "John", "Doe", "123-456-7890", 30, List.of("med1"), List.of("pollen")
        );

        assertEquals(personInfo1, personInfo2, "Two instances with the same values should be equal.");
    }

    @Test
    void testHashCode() {
        PersonMedicalInfo personInfo1 = new PersonMedicalInfo(
                "John", "Doe", "123-456-7890", 30, List.of("med1"), List.of("pollen")
        );
        PersonMedicalInfo personInfo2 = new PersonMedicalInfo(
                "John", "Doe", "123-456-7890", 30, List.of("med1"), List.of("pollen")
        );

        assertEquals(personInfo1.hashCode(), personInfo2.hashCode(), "Hash codes should be equal for equal objects.");
    }
}
