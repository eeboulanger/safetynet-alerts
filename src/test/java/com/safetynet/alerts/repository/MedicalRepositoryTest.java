package com.safetynet.alerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.util.IJsonDataEditor;
import com.safetynet.alerts.util.IJsonDataReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRepositoryTest {
    @Mock
    private IJsonDataEditor<MedicalRecord> editor;
    @Mock
    private IJsonDataReader reader;
    @InjectMocks
    private MedicalRecordRepository repository;
    private MedicalRecord medicalRecord;

    @BeforeEach
    public void setUp() {
        medicalRecord = new MedicalRecord(
                "Firstname",
                "Lastname",
                "birthdate",
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    @Test
    public void createTest() {
        when(editor.create(medicalRecord)).thenReturn(true);

        boolean isCreated = repository.create(medicalRecord);

        verify(editor).create(medicalRecord);
        assertTrue(isCreated);
    }

    @Test
    public void updateTest() {
        when(editor.update(medicalRecord)).thenReturn(true);

        boolean isUpdated = repository.update(medicalRecord);

        verify(editor).update(medicalRecord);
        assertTrue(isUpdated);
    }

    @Test
    public void deletedTest() {
        Map<String, String> map = Map.of(
                "firstname", medicalRecord.getFirstName(),
                "lastname", String.valueOf(medicalRecord.getLastName())
        );
        when(editor.delete(map)).thenReturn(true);

        boolean isDeleted = repository.delete(map);

        verify(editor).delete(map);
        assertTrue(isDeleted);
    }

    @Test
    public void findAllTest() {
        List<MedicalRecord> list = List.of(medicalRecord);

        when(reader.findAll(eq("medicalrecords"), any(TypeReference.class))).thenReturn(Optional.of(list));

        Optional<List<MedicalRecord>> optional = repository.findAll();

        verify(reader).findAll(eq("medicalrecords"), any(TypeReference.class));
        assertTrue(optional.isPresent());
        assertTrue(optional.get().contains(medicalRecord));
    }

    @Test
    public void findByNameTest() {
        List<MedicalRecord> list = List.of(medicalRecord);
        when(reader.findAll(eq("medicalrecords"), any(TypeReference.class))).thenReturn(Optional.of(list));

        Optional<MedicalRecord> result = repository.findByName(medicalRecord.getFirstName(), medicalRecord.getLastName());

        verify(reader).findAll(eq("medicalrecords"), any(TypeReference.class));
        assertTrue(result.isPresent());
        assertEquals(result.get(), medicalRecord);
    }

    @Test
    public void createFailsTest() {
        when(editor.create(medicalRecord)).thenReturn(false);

        boolean isCreated = repository.create(medicalRecord);

        verify(editor).create(medicalRecord);
        assertFalse(isCreated);
    }

    @Test
    public void updateFailsTest() {
        when(editor.update(medicalRecord)).thenReturn(false);

        boolean isUpdated = repository.update(medicalRecord);

        verify(editor).update(medicalRecord);
        assertFalse(isUpdated);
    }

    @Test
    public void deletedFailsTest() {
        Map<String, String> map = Map.of(
                "firstname", medicalRecord.getFirstName(),
                "lastname", String.valueOf(medicalRecord.getLastName())
        );
        when(editor.delete(map)).thenReturn(false);

        boolean isDeleted = repository.delete(map);

        verify(editor).delete(map);
        assertFalse(isDeleted);
    }

    @Test
    public void findAllFailsTest() {
        when(reader.findAll(eq("medicalrecords"), any(TypeReference.class))).thenReturn(Optional.empty());

        Optional<List<MedicalRecord>> optional = repository.findAll();

        verify(reader).findAll(eq("medicalrecords"), any(TypeReference.class));
        assertTrue(optional.isEmpty());
    }

    @Test
    public void findByNameFailsTest() {
        List<MedicalRecord> list = List.of(medicalRecord);

        when(reader.findAll(eq("medicalrecords"), any(TypeReference.class))).thenReturn(Optional.of(list));

        Optional<MedicalRecord> result = repository.findByName("No existing name", "test");

        verify(reader).findAll(eq("medicalrecords"), any(TypeReference.class));
        assertTrue(result.isEmpty());
    }
}
