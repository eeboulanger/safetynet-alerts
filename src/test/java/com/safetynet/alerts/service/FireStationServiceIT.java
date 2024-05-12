package com.safetynet.alerts.service;

import com.safetynet.alerts.DataPrepareService;
import com.safetynet.alerts.model.FireStation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FireStationServiceIT {

    @Autowired
    private FireStationService service;
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
    public void createFireStationTest() {
        FireStation station = new FireStation("Station address", 123);
        boolean isCreated = service.create(station);
        assertTrue(isCreated);
    }

    @Test
    public void updateFireStationTest() {
        FireStation station = dataPrepareService.getFireStation(0);
        boolean isUpdated = service.update(station);
        assertTrue(isUpdated);
    }

    @Test
    public void deleteFireStationTest() {
        FireStation station = dataPrepareService.getFireStation(0);
        boolean isDeleted = service.delete(Map.of(
                        "address", station.getAddress(),
                        "station", String.valueOf(station.getStation())
                )
        );
        assertTrue(isDeleted);
    }

    @Test
    public void findStationByAddressTest() {
        String address = dataPrepareService.getFireStation(0).getAddress();

        Optional<FireStation> optional = service.findStationByAddress(address);

        assertTrue(optional.isPresent());
    }

    @Test
    public void findStationByStationNumberTest() {
        int stationNumber = dataPrepareService.getFireStation(0).getStation();

        Optional<List<FireStation>> optional = service.findByStationNumber(stationNumber);

        assertTrue(optional.isPresent());
    }
}
