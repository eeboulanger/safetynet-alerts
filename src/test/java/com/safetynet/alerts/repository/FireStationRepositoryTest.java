package com.safetynet.alerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.IJsonDataEditor;
import com.safetynet.alerts.util.IJsonDataReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FireStationRepositoryTest {
    @Mock
    private IJsonDataEditor<FireStation> editor;
    @Mock
    private IJsonDataReader reader;
    @InjectMocks
    private FireStationRepository repository;
    private FireStation fireStation;

    @BeforeEach
    public void setUp() {
        fireStation = new FireStation(
                "Firestation address",
                1
        );
    }

    @Test
    public void createTest() {
        when(editor.create(fireStation)).thenReturn(true);

        boolean isCreated = repository.create(fireStation);

        verify(editor).create(fireStation);
        assertTrue(isCreated);
    }

    @Test
    public void updateTest() {
        when(editor.update(fireStation)).thenReturn(true);

        boolean isUpdated = repository.update(fireStation);

        verify(editor).update(fireStation);
        assertTrue(isUpdated);
    }

    @Test
    public void deletedTest() {
        Map<String, String> map = Map.of(
                "address", fireStation.getAddress(),
                "station", String.valueOf(fireStation.getStation())
        );
        when(editor.delete(map)).thenReturn(true);

        boolean isDeleted = repository.delete(map);

        verify(editor).delete(map);
        assertTrue(isDeleted);
    }

    @Test
    public void findAllTest() {
        List<FireStation> list = List.of(fireStation);

        when(reader.findAll(eq("firestations"), any(TypeReference.class))).thenReturn(Optional.of(list));

        Optional<List<FireStation>> optional = repository.findAll();

        verify(reader).findAll(eq("firestations"), any(TypeReference.class));
        assertTrue(optional.isPresent());
        assertTrue(optional.get().contains(fireStation));
    }

    @Test
    public void findByAddressTest() {
        List<FireStation> list = List.of(fireStation);
        when(reader.findAll(eq("firestations"), any(TypeReference.class))).thenReturn(Optional.of(list));

        Optional<FireStation> result = repository.findStationByAddress("Firestation address");

        verify(reader).findAll(eq("firestations"), any(TypeReference.class));
        assertTrue(result.isPresent());
        assertEquals(result.get(), fireStation);
    }

    @Test
    public void findByNumberTest() {
        List<FireStation> personList = List.of(fireStation);
        when(reader.findAll(eq("firestations"), any(TypeReference.class))).thenReturn(Optional.of(personList));

        Optional<List<FireStation>> result = repository.findByStationNumber(1);

        verify(reader).findAll(eq("firestations"), any(TypeReference.class));
        assertTrue(result.isPresent());
        assertTrue(result.get().contains(fireStation));
    }

    @Test
    public void createFailsTest() {
        when(editor.create(fireStation)).thenReturn(false);

        boolean isCreated = repository.create(fireStation);

        verify(editor).create(fireStation);
        assertFalse(isCreated);
    }

    @Test
    public void updateFailsTest() {
        when(editor.update(fireStation)).thenReturn(false);

        boolean isUpdated = repository.update(fireStation);

        verify(editor).update(fireStation);
        assertFalse(isUpdated);
    }

    @Test
    public void deletedFailsTest() {
        Map<String, String> map = Map.of(
                "address", fireStation.getAddress(),
                "station", String.valueOf(fireStation.getStation())
        );
        when(editor.delete(map)).thenReturn(false);

        boolean isDeleted = repository.delete(map);

        verify(editor).delete(map);
        assertFalse(isDeleted);
    }

    @Test
    public void findAllFailsTest() {
        when(reader.findAll(eq("firestations"), any(TypeReference.class))).thenReturn(Optional.empty());

        Optional<List<FireStation>> optional = repository.findAll();

        verify(reader).findAll(eq("firestations"), any(TypeReference.class));
        assertTrue(optional.isEmpty());
    }

    @Test
    public void findByAddressFailsTest() {
        List<FireStation> list = List.of(fireStation);

        when(reader.findAll(eq("firestations"), any(TypeReference.class))).thenReturn(Optional.of(list));

        Optional<FireStation> result = repository.findStationByAddress("No existing address");

        verify(reader).findAll(eq("firestations"), any(TypeReference.class));
        assertTrue(result.isEmpty());
    }

    @Test
    public void findByNameFailsTest() {
        List<FireStation> list = List.of(fireStation);
        when(reader.findAll(eq("firestations"), any(TypeReference.class))).thenReturn(Optional.of(list));

        Optional<List<FireStation>> result = repository.findByStationNumber(-1);

        verify(reader).findAll(eq("firestations"), any(TypeReference.class));
        assertTrue(result.isPresent());
        assertTrue(result.get().isEmpty());
    }
}
