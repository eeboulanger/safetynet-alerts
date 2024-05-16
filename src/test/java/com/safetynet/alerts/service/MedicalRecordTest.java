package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.IMedicalRecordRepository;
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
public class MedicalRecordTest {
    @Mock
    private IMedicalRecordRepository repository;

    @InjectMocks
    private MedicalRecordService service;

    private final MedicalRecord record = new MedicalRecord();

    private final Map<String, String> recordId = new HashMap<>();

    @Test
    public void findAllTest() {
        List<MedicalRecord> list = List.of(new MedicalRecord(
                "Firstname",
                "Lastname",
                "birthdate",
                null,
                null
        ));
        when(repository.findAll()).thenReturn(Optional.of(list));

        Optional<List<MedicalRecord>> records = service.findAll();

        assertTrue(records.isPresent());
        assertEquals(1, records.get().size());
    }

    @Test
    public void findByNameTest() {
        MedicalRecord person = new MedicalRecord();
        person.setFirstName("Firstname");
        person.setLastName("Lastname");
        when(repository.findByName("Firstname", "Lastname")).thenReturn(Optional.of(person));

        Optional<MedicalRecord> result = service.findByName("Firstname", "Lastname");

        verify(repository).findByName("Firstname", "Lastname");
        assertTrue(result.isPresent());
        assertEquals("Firstname", result.get().getFirstName());
    }

    @Test
    public void createNewRecordTest() {
        when(repository.create(record)).thenReturn(true);

        boolean result = service.create(record);

        verify(repository).create(record);
        assertTrue(result);
    }

    @Test
    public void updateRecordTest() {
        when(repository.update(record)).thenReturn(true);

        boolean result = service.update(record);

        verify(repository).update(record);
        assertTrue(result);
    }

    @Test
    public void deleterecordTest() {
        when(repository.delete(recordId)).thenReturn(true);

        boolean result = service.delete(recordId);

        verify(repository).delete(recordId);
        assertTrue(result);
    }
}
