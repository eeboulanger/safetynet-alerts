package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.IFireStationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

    @Mock
    private IFireStationRepository repository;

    @InjectMocks
    private FireStationService service;

    private final FireStation station = new FireStation();

    private final Map<String, String> stationId = new HashMap<>();

    @Test
    public void findAllTest() {
        List<FireStation> list = List.of(new FireStation(
                "address",
                1
        ));
        when(repository.findAll()).thenReturn(Optional.of(list));

        Optional<List<FireStation>> stations = service.findAll();

        assertTrue(stations.isPresent());
        assertEquals(1, stations.get().size());
    }

    @Test
    public void findByAddressTest() {
        FireStation fireStation = new FireStation("address", 1);

        when(repository.findStationByAddress("address")).thenReturn(Optional.of(fireStation));

        Optional<FireStation> result = service.findStationByAddress("address");

        verify(repository).findStationByAddress("address");
        assertTrue(result.isPresent());
        assertEquals("address", result.get().getAddress());
    }

    @Test
    public void findByNumberTest() {
        List<FireStation> list = List.of(new FireStation("address", 1));

        when(repository.findByStationNumber(1)).thenReturn(Optional.of(list));

        Optional<List<FireStation>> result = service.findByStationNumber(1);

        verify(repository).findByStationNumber(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
    }

    @Test
    public void createNewStationTest() {
        when(repository.create(station)).thenReturn(true);

        boolean result = service.create(station);

        verify(repository).create(station);
        assertTrue(result);
    }

    @Test
    public void updateStationTest() {
        when(repository.update(station)).thenReturn(true);

        boolean result = service.update(station);

        verify(repository).update(station);
        assertTrue(result);
    }

    @Test
    public void deleteStationTest() {
        when(repository.delete(stationId)).thenReturn(true);

        boolean result = service.delete(stationId);

        verify(repository).delete(stationId);
        assertTrue(result);
    }
}
