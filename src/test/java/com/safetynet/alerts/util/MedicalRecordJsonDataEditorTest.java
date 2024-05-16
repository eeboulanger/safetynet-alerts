package com.safetynet.alerts.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.safetynet.alerts.model.DataContainer;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordJsonDataEditorTest {

    @Mock
    private ObjectMapper mapper;
    @Mock
    private ObjectWriter objectWriter;
    private static DataContainer dataContainer;
    private static MedicalRecord record;
    @InjectMocks
    private MedicalRecordJsonDataEditor editor;

    @BeforeAll
    public static void setUpForAll() {
        //initialize data for testing
        dataContainer = new DataContainer();
        record = new MedicalRecord("FirstName", "LastName", "birthdate",
                new ArrayList<>(), new ArrayList<>());
        List<MedicalRecord> list = new ArrayList<>();
        list.add(record);
        dataContainer.setMedicalrecords(list);
    }

    @Nested
    public class SuccessfulEditingTests {
        @BeforeEach
        public void setUp() throws IOException {
            when(mapper.readValue(any(File.class), eq(DataContainer.class))).thenReturn(dataContainer);
            when(mapper.writerWithDefaultPrettyPrinter()).thenReturn(objectWriter);
            doNothing().when(objectWriter).writeValue(any(File.class), eq(dataContainer));
        }

        @Test
        public void createMedicalRecordTest() throws IOException {
            MedicalRecord medicalRecord = new MedicalRecord("New name", "new lastname", "", null, null);

            boolean isCreated = editor.create(medicalRecord);

            assertTrue(isCreated);
            verify(mapper).readValue(any(File.class), eq(DataContainer.class));
            verify(objectWriter).writeValue(any(File.class), eq(dataContainer));
        }

        @Test
        public void updateMedicalRecordTest() throws IOException {
            boolean isUpdated = editor.update(record);

            assertTrue(isUpdated);
            verify(mapper).readValue(any(File.class), eq(DataContainer.class));
            verify(objectWriter).writeValue(any(File.class), eq(dataContainer));
        }

        @Test
        public void deleteMedicalRecordTest() throws IOException {
            boolean isDeleted = editor.delete(Map.of(
                    "firstname", record.getFirstName(),
                    "lastname", record.getLastName())
            );

            assertTrue(isDeleted);
            verify(mapper).readValue(any(File.class), eq(DataContainer.class));
            verify(objectWriter).writeValue(any(File.class), eq(dataContainer));
        }
    }

    @Nested
    public class EditingFailsTests {
        @BeforeEach
        public void setUp() throws IOException {
            when(mapper.readValue(any(File.class), eq(DataContainer.class))).thenReturn(dataContainer);
        }

        @Test
        @DisplayName("Create record when a record with the same name exists should fail")
        public void createMedicalRecord_whenAlreadyExists_shouldFail() {

            boolean result = editor.create(record); //medical record exists already

            assertFalse(result);
        }

        @Test
        @DisplayName("Update when no record with the name exists should fail")
        public void updateMedicalRecord_whenNoRecordExists_shouldFail() {
            MedicalRecord medicalRecord = new MedicalRecord("No such name", "test", null, null, null);
            boolean result = editor.update(medicalRecord); //no medical record exists

            assertFalse(result);
        }

        @Test
        @DisplayName("Delete when no record with the name exists should fail")
        public void deleteMedicalRecord_whenNoRecordExists_shouldFail() {
            MedicalRecord medicalRecord = new MedicalRecord("No such name", "test", null, null, null);
            boolean result = editor.delete(Map.of(
                    "firstname", medicalRecord.getFirstName(),
                    "lastname", medicalRecord.getLastName())
            );

            assertFalse(result);
        }

    }

    @Nested
    public class ReadValueFailsTests {
        @BeforeEach
        public void setUp() throws IOException {
            when(mapper.readValue(any(File.class), eq(DataContainer.class))).thenThrow(IOException.class);
        }

        @Test
        @DisplayName("Create record when unable to read data should fail")
        public void createRecord_whenFailsToReadData_shouldFail() throws IOException {
            boolean result = editor.create(record);

            verify(mapper).readValue(any(File.class), eq(DataContainer.class));
            assertFalse(result);
        }

        @Test
        @DisplayName("Update when unable to read data should fail")
        public void updateRecord_whenFailsToReadData_shouldFail() throws IOException {

            boolean result = editor.update(record);

            verify(mapper).readValue(any(File.class), eq(DataContainer.class));
            assertFalse(result);
        }

        @Test
        @DisplayName("Delete when unable to read data should fail")
        public void deleteRecord_whenFailsToReadData_shouldFail() throws IOException {

            boolean result = editor.delete(Map.of(
                    "firstname", record.getFirstName(),
                    "lastname", record.getLastName()));

            verify(mapper).readValue(any(File.class), eq(DataContainer.class));
            assertFalse(result);
        }
    }


    @Nested
    public class WriteValueFailsTests {
        @BeforeEach
        public void setUp() throws IOException {
            when(mapper.readValue(any(File.class), eq(DataContainer.class))).thenReturn(dataContainer);
            when(mapper.writerWithDefaultPrettyPrinter()).thenReturn(objectWriter);
            doThrow(IOException.class).when(objectWriter).writeValue(any(File.class), eq(dataContainer));

        }

        @Test
        @DisplayName("Create when unable to write data should fail")
        public void createFireStation_whenFailsToWriteData_shouldFail() {
            MedicalRecord newRecord = new MedicalRecord("firstname", "lastname", null, null, null);
            boolean result = editor.create(newRecord);

            assertFalse(result);
        }

        @Test
        @DisplayName("Update when unable to write data should fail")
        public void updateFireStation_whenFailsToWriteData_shouldFail() {
            boolean result = editor.update(record);

            assertFalse(result);
        }

        @Test
        @DisplayName("Delete fire station when unable to write data should fail")
        public void deleteFireStation_whenFailsToWriteData_shouldFail() {

            boolean result = editor.delete(Map.of(
                    "firstname", record.getFirstName(),
                    "lastname", record.getLastName()));

            assertFalse(result);
        }
    }
}
