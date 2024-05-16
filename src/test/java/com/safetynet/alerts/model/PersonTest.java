package com.safetynet.alerts.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void testConstructorAndFieldAccess() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String address = "123 Main Street";
        String city = "Anytown";
        int zip = 12345;
        String phone = "555-1234";
        String email = "john.doe@example.com";

        // Act
        Person person = new Person(firstName, lastName, address, city, zip, phone, email);

        // Assert
        assertEquals(firstName, person.getFirstName(), "First name should match the constructor argument.");
        assertEquals(lastName, person.getLastName(), "Last name should match the constructor argument.");
        assertEquals(address, person.getAddress(), "Address should match the constructor argument.");
        assertEquals(city, person.getCity(), "City should match the constructor argument.");
        assertEquals(zip, person.getZip(), "Zip code should match the constructor argument.");
        assertEquals(phone, person.getPhone(), "Phone number should match the constructor argument.");
        assertEquals(email, person.getEmail(), "Email should match the constructor argument.");
    }

    @Test
    void testEquality() {
        // Arrange
        Person person1 = new Person("John", "Doe", "123 Main Street", "Anytown", 12345, "555-1234", "john.doe@example.com");
        Person person2 = new Person("John", "Doe", "123 Main Street", "Anytown", 12345, "555-1234", "john.doe@example.com");

        // Act & Assert
        assertEquals(person1, person2, "Two instances with the same values should be equal.");
    }

    @Test
    void testHashCode() {
        // Arrange
        Person person1 = new Person("John", "Doe", "123 Main Street", "Anytown", 12345, "555-1234", "john.doe@example.com");
        Person person2 = new Person("John", "Doe", "123 Main Street", "Anytown", 12345, "555-1234", "john.doe@example.com");

        // Act & Assert
        assertEquals(person1.hashCode(), person2.hashCode(), "Hash codes should be equal for equal objects.");
    }
}
