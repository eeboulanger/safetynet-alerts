package com.safetynet.alerts.repository;

import com.safetynet.alerts.DataPrepareService;
import com.safetynet.alerts.model.FireStation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FireStationRepositoryIT {
    @Autowired
    private FireStationRepository repository;
    private DataPrepareService dataPrepareService;

    @BeforeEach
    public void setUp() {
        dataPrepareService = new DataPrepareService();
    }
    @AfterEach
    public void tearDown() throws IOException {
        dataPrepareService.resetData();
    }

    @Test
    public void deleteFireStationTest() {
        FireStation station = dataPrepareService.getFireStation(0);
        System.out.println("station " +station.getAddress() + " " + station.getStation());
        boolean isDeleted = repository.delete(Map.of(
                "address", station.getAddress(),
                "station", String.valueOf(station.getStation())
        ));
        assertTrue(isDeleted);
    }

    @Test
    public void updateFireStationTest() {
        FireStation station = dataPrepareService.getFireStation(0);
        station.setStation(20);

        boolean isUpdated = repository.update(station);

        assertTrue(isUpdated);
    }

    @Test
    public void createFireStationTest() {
        FireStation station = new FireStation("Firestation address", 123);

        boolean isCreated = repository.create(station);

        assertTrue(isCreated);
    }

    @Test
    public void getAllPersonsFromJsonTest() {
        Optional<List<FireStation>> optional = repository.findAll();
        List<FireStation> stations = new ArrayList<>();
        if (optional.isPresent()) {
            stations = optional.get();
        }
        assertFalse(stations.isEmpty());
        assertEquals("1509 Culver St", stations.get(0).getAddress());
        assertEquals(3, stations.get(0).getStation());
    }

    @Test
    public void getFireStationsByNumberTest() {
        Optional<List<FireStation>> optionalList = repository.findByStationNumber(3);
        List<FireStation> stations = new ArrayList<>();
        if (optionalList.isPresent()) {
            stations = optionalList.get();
        }

        assertFalse(stations.isEmpty());
        assertEquals(5, stations.size());
    }

    @Test
    public void findByStationByAddressTest() {
        Optional<FireStation> optional = repository.findStationByAddress("947 E. Rose Dr");
        FireStation result = optional.orElse(new FireStation());

        assertEquals(1, result.getStation());
    }
}