package com.safetynet.alerts.util;

import com.safetynet.alerts.DataPrepareService;
import com.safetynet.alerts.model.FireStation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FireStationJsonDataEditorTest {

    private final FireStationJsonDataEditor editor = new FireStationJsonDataEditor();
    private static DataPrepareService dataPrepareService;

    @BeforeAll
    public static void setUp() {
        dataPrepareService = new DataPrepareService();
    }

    @AfterEach
    public void tearDown() throws IOException {
        dataPrepareService.resetData();
    }

    @Test
    public void createFireStationTest() throws IOException {
        FireStation fireStation = new FireStation("Address", 1);
        boolean isCreated = editor.create(fireStation);

        int lastStation = dataPrepareService.getData().getFirestations().size() - 1;

        assertTrue(isCreated);
        assertEquals("Address", dataPrepareService.getData().getFirestations().get(lastStation).getAddress());
        assertEquals(1, dataPrepareService.getData().getFirestations().get(lastStation).getStation());
    }

    @Test
    public void createFireStation_whenFireStationExists_shouldFail() {
        FireStation existingFirestation = dataPrepareService.getFireStation(2);
        assertFalse(editor.create(existingFirestation));
    }

    @Test
    public void updateFireStationNumberTest() throws IOException {
        FireStation existingFireStation = dataPrepareService.getFireStation(1);
        existingFireStation.setStation(10); //Update station number

        assertTrue(editor.update(existingFireStation));
        assertEquals(10, dataPrepareService.getData().getFirestations().get(1).getStation());
    }

    @Test
    public void updateFireStation_whenNoSuchFireStation_shouldFail() {
        FireStation unexistingFirestation = new FireStation("Empty", -1);
        assertFalse(editor.update(unexistingFirestation));
    }

    @Test
    public void deleteFireStationTest() throws IOException {
        FireStation existingFirestation = dataPrepareService.getFireStation(0);
        int numberOfStations = dataPrepareService.getFireStations().size();

        boolean isDeleted = editor.delete(Map.of(
                        "address", existingFirestation.getAddress(),
                        "station", String.valueOf(existingFirestation.getStation())
                )
        );

        assertTrue(isDeleted);
        assertEquals(numberOfStations - 1, dataPrepareService.getData().getFirestations().size());
    }

    @Test
    public void deleteFireStation_whenNoSuchFireStation_shouldFail() {
        Map<String, String> firestation = Map.of(
                "address", "no address",
                "station", "-1"
        );
        assertFalse(editor.delete(firestation));
    }
}
