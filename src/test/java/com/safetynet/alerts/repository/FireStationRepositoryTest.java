package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.FireStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FireStationRepositoryTest {
    private final FireStationRepository repository = new FireStationRepository();
    private FireStation fireStation;

    @BeforeEach
    public void setUp() {
        fireStation = new FireStation(
                "Elm street",
                12);

        repository.saveAll(new ArrayList<>(List.of(fireStation)));
    }

    @Test
    public void createTest() {
        FireStation station = new FireStation("New firestation", 21);

        boolean isCreated = repository.create(station);

        assertTrue(isCreated);
    }

    @Test
    public void updateTest() {
        fireStation.setStation(123);
        boolean isUpdated = repository.update(fireStation);

        assertTrue(isUpdated);
    }

    @Test
    public void deletedTest() {
        Map<String, String> map = Map.of(
                "address", fireStation.getAddress(),
                "station", String.valueOf(fireStation.getStation())
        );
        boolean isDeleted = repository.delete(map);

        assertTrue(isDeleted);
    }

    @Test
    public void findAllTest() {
        Optional<List<FireStation>> optional = repository.findAll();

        assertTrue(optional.isPresent());
        assertTrue(optional.get().contains(fireStation));
    }

    @Test
    public void findByAddressTest() {
        Optional<FireStation> result = repository.findStationByAddress(fireStation.getAddress());

        assertTrue(result.isPresent());
        assertEquals(result.get(), fireStation);
    }

    @Test
    public void findByStationNumberTest() {
        Optional<List<FireStation>> result = repository.findByStationNumber(fireStation.getStation());

        assertTrue(result.isPresent());
        assertEquals(result.get().get(0), fireStation);
    }

    @Test
    @DisplayName("Creating existing fire station should fail")
    public void createFailsTest() {
        boolean isCreated = repository.create(fireStation);

        assertFalse(isCreated);
    }

    @Test
    @DisplayName("Updating a fire station that's not in the list should fail")
    public void updateFailsTest() {
        FireStation station = new FireStation("No such address", 12);
        boolean isUpdated = repository.update(station);

        assertFalse(isUpdated);
    }

    @Test
    @DisplayName("Updating address should fail")
    public void updateAddressFailsTest() {
        FireStation station = new FireStation("New address", 12);
        boolean isUpdated = repository.update(station);

        assertFalse(isUpdated);
    }

    @Test
    @DisplayName("Deleting a firestation that doesn't exist should fail")
    public void deletedFailsTest() {
        Map<String, String> map = Map.of(
                "address", "No such address",
                "station", "12"
        );

        boolean isDeleted = repository.delete(map);

        assertFalse(isDeleted);
    }

    @Test
    @DisplayName("Find all when empty list should return empty optional")
    public void findAllFailsTest() {
        repository.saveAll(new ArrayList<>());
        Optional<List<FireStation>> optional = repository.findAll();

        assertTrue(optional.isPresent());
        assertTrue(optional.get().isEmpty());
    }

    @Test
    @DisplayName("Find by address when no result should return empty")
    public void findByAddressFailsTest() {
        Optional<FireStation> result = repository.findStationByAddress("No such address");

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Find by station when no result should return empty")
    public void findByStationNumberFailsTest() {
        Optional<List<FireStation>> result = repository.findByStationNumber(-1);

        assertTrue(result.isEmpty());
    }
}
