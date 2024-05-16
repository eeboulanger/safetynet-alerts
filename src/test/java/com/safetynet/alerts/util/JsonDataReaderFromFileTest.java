package com.safetynet.alerts.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JsonDataReaderFromFileTest {

    @Mock
    private ObjectMapper mapper;
    @Mock
    private JsonNode jsonNode;
    @Mock
    private JsonNode modelNode;

    @InjectMocks
    private JsonDataReaderFromFile reader;

    @Nested
    public class ReadSuccessTests {

        @BeforeEach
        public void setUp() throws IOException {
            when(mapper.readTree(any(File.class))).thenReturn(jsonNode);
        }

        @Test
        public void findAllPersonsTest() {
            List<Person> listOfPersons = new ArrayList<>();
            listOfPersons.add(new Person());
            when(jsonNode.get("persons")).thenReturn(modelNode);
            when(mapper.convertValue(any(JsonNode.class), any(TypeReference.class))).thenReturn(listOfPersons);

            Optional<List<Person>> optional = reader.findAll("persons", new TypeReference<>() {
            });

            assertNotNull(optional.get());
        }

        @Test
        public void findAllFireStationsTest() {
            List<FireStation> list = new ArrayList<>();
            list.add(new FireStation());
            when(jsonNode.get("firestations")).thenReturn(modelNode);
            when(mapper.convertValue(any(JsonNode.class), any(TypeReference.class))).thenReturn(list);

            Optional<List<FireStation>> optional = reader.findAll("firestations", new TypeReference<>() {
            });

            assertNotNull(optional.get());
        }

        @Test
        public void findAllMedicalRecordsTest() {
            List<MedicalRecord> list = new ArrayList<>();
            list.add(new MedicalRecord());
            when(jsonNode.get("medicalrecords")).thenReturn(modelNode);
            when(mapper.convertValue(any(JsonNode.class), any(TypeReference.class))).thenReturn(list);

            Optional<List<MedicalRecord>> optional = reader.findAll("medicalrecords", new TypeReference<>() {
            });

            assertNotNull(optional.get());
        }
    }

    @Nested
    public class ReadFailsTests {
        @BeforeEach
        public void setUp() throws IOException {
            //fails to read data from file
            when(mapper.readTree(any(File.class))).thenThrow(IOException.class);
        }

        @Test
        public void findAllPersonsFailsTest() throws IOException {
            Optional<List<Person>> optional = reader.findAll("persons", new TypeReference<>() {
            });
            assertTrue(optional.isEmpty());
        }

        @Test
        public void findAllFireStationsFailsTest() throws IOException {
            Optional<List<Person>> optional = reader.findAll("firestations", new TypeReference<>() {
            });
            assertTrue(optional.isEmpty());
        }

        @Test
        public void findAllMedicalRecordsFailsTest() throws IOException {
            Optional<List<Person>> optional = reader.findAll("medicalrecords", new TypeReference<>() {
            });
            assertTrue(optional.isEmpty());
        }
    }
}
