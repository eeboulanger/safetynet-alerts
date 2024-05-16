package com.safetynet.alerts.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonContactInfoTest {

    @Test
    void testConstructorAndFieldAccess() {
        String firstName = "John";
        String lastName = "Doe";
        String address = "123 Maple St";
        String phoneNumber = "555-1234";

        PersonContactInfo personInfo = new PersonContactInfo(firstName, lastName, address, phoneNumber);

        assertEquals(firstName, personInfo.getFirstName());
        assertEquals(lastName, personInfo.getLastName());
        assertEquals(address, personInfo.getAddress());
        assertEquals(phoneNumber, personInfo.getPhoneNumber());
    }

    @Test
    void testEquality() {
        PersonContactInfo personInfo1 = new PersonContactInfo("John", "Doe", "123 Maple St", "555-1234");
        PersonContactInfo personInfo2 = new PersonContactInfo("John", "Doe", "123 Maple St", "555-1234");

        assertEquals(personInfo1, personInfo2, "Two instances with the same values should be equal.");
    }

    @Test
    void testHashCode() {
        PersonContactInfo personInfo1 = new PersonContactInfo("John", "Doe", "123 Maple St", "555-1234");
        PersonContactInfo personInfo2 = new PersonContactInfo("John", "Doe", "123 Maple St", "555-1234");

        assertEquals(personInfo1.hashCode(), personInfo2.hashCode(), "Hash codes should be equal for equal objects.");
    }
}
