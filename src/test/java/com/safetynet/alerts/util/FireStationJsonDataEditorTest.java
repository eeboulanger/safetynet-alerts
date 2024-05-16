package com.safetynet.alerts.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.safetynet.alerts.model.DataContainer;
import com.safetynet.alerts.model.FireStation;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FireStationJsonDataEditorTest {

    @Mock
    private ObjectMapper mapper;
    @Mock
    private ObjectWriter objectWriter;
    private static DataContainer dataContainer;
    private static FireStation station;
    @InjectMocks
    private FireStationJsonDataEditor editor;

    @BeforeAll
    public static void setUpForAll() {
        //initialize data for testing
        dataContainer = new DataContainer();
        station = new FireStation("Firestation1", 1);
        List<FireStation> list = new ArrayList<>();
        list.add(station);
        dataContainer.setFirestations(list);
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
        public void createFireStationTest() throws IOException {
            FireStation newStation = new FireStation("New station", 123);

            boolean isCreated = editor.create(newStation);

            assertTrue(isCreated);
            verify(mapper).readValue(any(File.class), eq(DataContainer.class));
            verify(objectWriter).writeValue(any(File.class), eq(dataContainer));
        }

        @Test
        public void updateFireStationTest() throws IOException {
            boolean isUpdated = editor.update(station);

            assertTrue(isUpdated);
            verify(mapper).readValue(any(File.class), eq(DataContainer.class));
            verify(objectWriter).writeValue(any(File.class), eq(dataContainer));
        }

        @Test
        public void deleteFireStationTest() throws IOException {
            boolean isDeleted = editor.delete(Map.of(
                    "address", station.getAddress(),
                    "station", String.valueOf(station.getStation())
            ));

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
        @DisplayName("Create fire station when a station with the same address and number exists should fail")
        public void createFireStation_whenStationExists_shouldFail() {

            boolean result = editor.create(station); //station exists already

            assertFalse(result);
        }

        @Test
        @DisplayName("Update fire station when no station with the address exists should fail")
        public void updateFireStation_whenNoStationExists_shouldFail() {
            FireStation fireStation = new FireStation("No such address", 1);
            boolean result = editor.update(fireStation); //no such station exists

            assertFalse(result);
        }

        @Test
        @DisplayName("Delete fire station when no station with the address and number exists should fail")
        public void deleteFireStation_whenNoStationExists_shouldFail() {
            FireStation fireStation = new FireStation("No such address", 1);
            boolean result = editor.delete(Map.of(
                    "address", fireStation.getAddress(),
                    "station", String.valueOf(fireStation.getStation())
            )); //station exists already

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
        @DisplayName("Create fire station when unable to read data should fail")
        public void createFireStation_whenFailsToReadData_shouldFail() throws IOException {
            FireStation station = new FireStation("address", 1);

            boolean result = editor.create(station);

            verify(mapper).readValue(any(File.class), eq(DataContainer.class));
            assertFalse(result);
            assertNull(editor.getFireStationList());
        }

        @Test
        @DisplayName("Update fire station when unable to read data should fail")
        public void updateFireStation_whenFailsToReadData_shouldFail() throws IOException {
            FireStation station = new FireStation("address", 1);

            boolean result = editor.update(station);

            verify(mapper).readValue(any(File.class), eq(DataContainer.class));
            assertFalse(result);
            assertNull(editor.getFireStationList());
        }

        @Test
        @DisplayName("Delete fire station when unable to read data should fail")
        public void deleteFireStation_whenFailsToReadData_shouldFail() throws IOException {
            FireStation station = new FireStation("address", 1);

            boolean result = editor.delete(Map.of(
                    "address", "New address",
                    "station", "1"));

            verify(mapper).readValue(any(File.class), eq(DataContainer.class));
            assertFalse(result);
            assertNull(editor.getFireStationList());
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
        @DisplayName("Create fire station when unable to write data should fail")
        public void createFireStation_whenFailsToWriteData_shouldFail() {
            FireStation station = new FireStation("address", 1);

            boolean result = editor.create(station);

            assertFalse(result);
        }

        @Test
        @DisplayName("Update fire station when unable to write data should fail")
        public void updateFireStation_whenFailsToWriteData_shouldFail() {
            boolean result = editor.update(station);

            assertFalse(result);
        }

        @Test
        @DisplayName("Delete fire station when unable to write data should fail")
        public void deleteFireStation_whenFailsToWriteData_shouldFail() {

            boolean result = editor.delete(Map.of(
                    "address", station.getAddress(),
                    "station", String.valueOf(station.getStation())));

            assertFalse(result);
        }
    }
}
