package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.IFireStationRepository;
import org.junit.jupiter.api.BeforeEach;
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
    private List<FireStation> fireStationList;

    @BeforeEach
    public void setUp() {
        fireStationList = List.of(new FireStation(
                "Holmart street",
                11
        ));
    }

    @Test
    public void findAllTest() {
        when(repository.findAll()).thenReturn(Optional.of(fireStationList));

        Optional<List<FireStation>> stations = service.findAll();

        assertTrue(stations.isPresent());
        assertEquals(fireStationList, stations.get());
    }

    @Test
    public void findByAddressTest() {
        String address = fireStationList.get(0).getAddress();
        when(repository.findStationByAddress(address)).thenReturn(Optional.of(fireStationList.get(0)));

        Optional<FireStation> result = service.findStationByAddress(address);

        verify(repository).findStationByAddress(address);
        assertTrue(result.isPresent());
        assertEquals(address, result.get().getAddress());
    }

    @Test
    public void findByNumberTest() {
        int station = fireStationList.get(0).getStation();
        when(repository.findByStationNumber(station)).thenReturn(Optional.of(fireStationList));

        Optional<List<FireStation>> result = service.findByStationNumber(station);

        verify(repository).findByStationNumber(station);
        assertTrue(result.isPresent());
        assertTrue(result.get().contains(fireStationList.get(0)));
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
