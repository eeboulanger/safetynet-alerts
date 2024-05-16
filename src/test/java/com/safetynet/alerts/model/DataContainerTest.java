package com.safetynet.alerts.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class DataContainerTest {

    @Test
    void testDefaultConstructorAndSettersGetters() {

        DataContainer dataContainer = new DataContainer();
        List<Person> persons = List.of(new Person("John", "Doe", "123 Elm St", "City", 1234, "555-1234", "john.doe@example.com"));
        List<FireStation> firestations = List.of(new FireStation("123 Elm st", 1));
        List<MedicalRecord> medicalRecords = List.of(new MedicalRecord("John", "Doe", "01/01/1990", List.of("med1"), List.of("allergy1")));


        dataContainer.setPersons(persons);
        dataContainer.setFirestations(firestations);
        dataContainer.setMedicalrecords(medicalRecords);

        assertSame(persons, dataContainer.getPersons());
        assertSame(firestations, dataContainer.getFirestations());
        assertSame(medicalRecords, dataContainer.getMedicalrecords());
    }

    @Test
    void testEquality() {
        DataContainer dataContainer1 = new DataContainer();
        DataContainer dataContainer2 = new DataContainer();

        assertEquals(dataContainer1, dataContainer2, "Two new instances should be equal if they have not been modified.");

        List<Person> persons = List.of(new Person("John", "Doe", "123 Elm St", "City", 12345, "555-1234", "john.doe@example.com"));
        dataContainer1.setPersons(persons);
        dataContainer2.setPersons(persons);

        assertEquals(dataContainer1, dataContainer2, "Instances with the same data should be equal.");
    }

    @Test
    void testHashCode() {
        DataContainer dataContainer1 = new DataContainer();
        DataContainer dataContainer2 = new DataContainer();

        assertEquals(dataContainer1.hashCode(), dataContainer2.hashCode(), "Hash codes should be equal for new instances.");

        List<Person> persons = List.of(new Person("John", "Doe", "123 Elm St", "City", 12345, "555-1234", "john.doe@example.com"));
        dataContainer1.setPersons(persons);
        dataContainer2.setPersons(persons);

        assertEquals(dataContainer1.hashCode(), dataContainer2.hashCode(), "Hash codes should be equal for instances with the same data.");
    }
}
