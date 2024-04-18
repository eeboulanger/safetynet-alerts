package com.safetynet.alerts.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class JsonDataReaderTest {

    @Test
    public void getAllPersonsFromJsonTest() {
        Optional<List<Person>> optionalList = JsonDataReader.findAll("persons", new TypeReference<List<Person>>() {
        });
        List<Person> list = new ArrayList<>();
        if (optionalList.isPresent()) {
            list = optionalList.get();
        }
        assertFalse(list.isEmpty());
        assertEquals("John", list.get(0).getFirstName());
    }

    @Test
    public void getAllFirestationsFromJsonTest() {
        Optional<List<FireStation>> optionalList = JsonDataReader.findAll("firestations", new TypeReference<List<FireStation>>() {
        });
        List<FireStation> list = new ArrayList<>();
        if (optionalList.isPresent()) {
            list = optionalList.get();
        }
        assertFalse(list.isEmpty());
        assertEquals("1509 Culver St", list.get(0).getAddress());
        assertEquals(3, list.get(0).getStation());
    }

    @Test
    public void getAllMedicalRecordsFromJsonTest() {
        Optional<List<MedicalRecord>> optionalList = JsonDataReader.findAll("medicalrecords", new TypeReference<List<MedicalRecord>>() {
        });
        List<MedicalRecord> list = new ArrayList<>();
        if (optionalList.isPresent()) {
            list = optionalList.get();
        }
        assertFalse(list.isEmpty());
        assertEquals("John", list.get(0).getFirstName());
        assertEquals("aznol:350mg", list.get(0).getMedications().get(0));
        assertEquals(0, list.get(1).getAllergies().size());
    }

    @Test
    public void noObjectsInJsonReturnsEmpty() {
        Optional<List<MedicalRecord>> optionalList = JsonDataReader.findAll("medical", new TypeReference<List<MedicalRecord>>() {
        });

        assertTrue(optionalList.isEmpty());
    }
}
