package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.FireStation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FireStationRepositoryTest {

    private final FireStationRepository repository = new FireStationRepository();

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