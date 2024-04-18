package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.FireStation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
}
