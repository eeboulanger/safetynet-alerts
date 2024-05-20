package com.safetynet.alerts.service;

import com.safetynet.alerts.config.DataInitializer;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.FireStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FireStationServiceIT {
    @Autowired
    private FireStationService service;
    @Autowired
    private FireStationRepository repository;
    @Autowired
    private DataInitializer dataInitializer;
    private FireStation station;


    @BeforeEach
    public void setUp() {
        dataInitializer.run();
        station = repository.findAll().isPresent() ? repository.findAll().get().get(0) : new FireStation();
    }

    @Test
    public void createFireStationTest() {
        FireStation fireStation = new FireStation("Elm street", 12);
        boolean isCreated = service.create(fireStation);
        assertTrue(isCreated);
    }

    @Test
    public void updateFireStationTest() {
        station.setStation(13);
        boolean isUpdated = service.update(station);
        assertTrue(isUpdated);
    }

    @Test
    public void deleteFireStationTest() {
        boolean isDeleted = service.delete(Map.of(
                        "address", station.getAddress(),
                        "station", String.valueOf(station.getStation())
                )
        );
        assertTrue(isDeleted);
    }

    @Test
    public void findStationByAddressTest() {
        Optional<FireStation> optional = service.findStationByAddress(station.getAddress());

        assertTrue(optional.isPresent());
    }

    @Test
    public void findStationByStationNumberTest() {
        Optional<List<FireStation>> optional = service.findByStationNumber(station.getStation());

        assertTrue(optional.isPresent());
    }

    @Test
    public void findAllTest() {
        Optional<List<FireStation>> result = service.findAll();
        assertTrue(result.isPresent());
        assertEquals(13, result.get().size());
    }
}
