package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.IMedicalRecordRepository;
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
public class MedicalRecordTest {
    @Mock
    private IMedicalRecordRepository repository;
    @InjectMocks
    private MedicalRecordService service;

    private final MedicalRecord record = new MedicalRecord();
    private final Map<String, String> recordId = new HashMap<>();
    private List<MedicalRecord> medicalRecordList;

    @BeforeEach
    public void setUp() {
        medicalRecordList = List.of(new MedicalRecord(
                "Johanna",
                "Evergreen",
                "01/01/1970",
                List.of("30mg:Holdex"),
                List.of("cats", "pollen")
        ));
    }

    @Test
    public void findAllTest() {
        when(repository.findAll()).thenReturn(Optional.of(medicalRecordList));

        Optional<List<MedicalRecord>> records = service.findAll();

        assertTrue(records.isPresent());
        assertTrue(records.get().containsAll(medicalRecordList));
    }

    @Test
    public void findByNameTest() {
        String firstName = medicalRecordList.get(0).getFirstName();
        String lastName = medicalRecordList.get(0).getLastName();
        when(repository.findByName(firstName, lastName))
                .thenReturn(Optional.of(medicalRecordList.get(0)));

        Optional<MedicalRecord> result = service.findByName(firstName, lastName);

        verify(repository).findByName(firstName, lastName);
        assertTrue(result.isPresent());
        assertEquals(firstName, result.get().getFirstName());
        assertEquals(lastName, result.get().getLastName());
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
    public void deleteRecordTest() {
        when(repository.delete(recordId)).thenReturn(true);

        boolean result = service.delete(recordId);

        verify(repository).delete(recordId);
        assertTrue(result);
    }
}
