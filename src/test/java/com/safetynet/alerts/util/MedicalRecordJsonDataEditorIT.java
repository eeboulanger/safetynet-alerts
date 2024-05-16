package com.safetynet.alerts.util;

import com.safetynet.alerts.DataPrepareService;
import com.safetynet.alerts.model.MedicalRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MedicalRecordJsonDataEditorIT {
    @Autowired
    private  MedicalRecordJsonDataEditor editor;
    private DataPrepareService dataPrepareService;

    @BeforeEach
    public void setUp() {
        dataPrepareService = new DataPrepareService();
    }

    @AfterEach
    public void tearDown() throws IOException {
        dataPrepareService.resetData();
    }

    @Test
    public void createMedicalRecordTest() throws IOException {
        MedicalRecord record = new MedicalRecord(
                "New person",
                "lastname",
                "01/01/1950",
                new ArrayList<>(),
                new ArrayList<>()
        );
        int initialNumberOfRecords = dataPrepareService.getMedicalRecordsList().size();

        boolean isCreated = editor.create(record);

        assertTrue(isCreated);
        assertEquals(initialNumberOfRecords + 1, dataPrepareService.getData().getMedicalrecords().size());
    }

    @Test
    @DisplayName("Given there is already a medical record with the same name, then create should fail")
    public void createMedicalRecordFailsTest() throws IOException {
        MedicalRecord record = dataPrepareService.getMedicalRecord(0);
        int initialNumberOfRecords = dataPrepareService.getMedicalRecordsList().size();

        boolean isCreated = editor.create(record);

        assertFalse(isCreated);
        assertEquals(initialNumberOfRecords, dataPrepareService.getData().getMedicalrecords().size());
    }

    @Test
    public void updateMedicalRecordTest() throws IOException {
        MedicalRecord record = dataPrepareService.getMedicalRecord(1);
        record.setBirthdate("01/01/1999");

        boolean isUpdated = editor.update(record);

        MedicalRecord result = dataPrepareService.getData().getMedicalrecords().get(1);
        assertTrue(isUpdated);
        assertEquals("01/01/1999", result.getBirthdate());
    }

    @Test
    @DisplayName("Given there is no record with the given name, then update should fail")
    public void updateMedicalRecordFailsTest() {
        MedicalRecord record = new MedicalRecord(
                "No such name",
                "lastname",
                "",
                new ArrayList<>(),
                new ArrayList<>()
        );

        boolean isUpdated = editor.update(record);

        assertFalse(isUpdated);
    }

    @Test
    public void deleteMedicalRecordTest() throws IOException {
        MedicalRecord record = dataPrepareService.getMedicalRecord(0);
        int initialNumberOfRecords = dataPrepareService.getMedicalRecordsList().size();

        boolean isDeleted = editor.delete(Map.of(
                "firstname", record.getFirstName(),
                "lastname", record.getLastName()
        ));

        assertTrue(isDeleted);
        assertEquals(initialNumberOfRecords - 1, dataPrepareService.getData().getMedicalrecords().size());
    }

    @Test
    @DisplayName("Given there is no such record, then delete should fail")
    public void deleteMedicalRecordFailsTest() throws IOException {
        int initialNumberOfRecords = dataPrepareService.getMedicalRecordsList().size();

        boolean isDeleted = editor.delete(Map.of(
                        "firstname", "no such name",
                        "lastname", "test"
                )
        );

        assertFalse(isDeleted);
        assertEquals(initialNumberOfRecords, dataPrepareService.getData().getMedicalrecords().size());
    }
}
