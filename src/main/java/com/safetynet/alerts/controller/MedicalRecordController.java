package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService recordService;

    @PostMapping("/medicalRecord")
    public String createMedicalRecord(@RequestBody MedicalRecord record) {
        return recordService.create(record) ? "Medical record has been successfully created" : "Creating medical record has failed";
    }

    @PutMapping("/medicalRecord")
    public String updateMedicalRecord(@RequestBody MedicalRecord record) {
        return recordService.update(record) ? "Medical record has been successfully updated" : "Updating medical record has failed";
    }

    @DeleteMapping("/medicalRecord")
    public String deleteMedicalRecord(@RequestBody MedicalRecord record) {
        return recordService.delete(Map.of(
                        "firstname", record.getFirstName(),
                        "lastname", record.getLastName()
                )
        ) ? "Medical record has been successfully deleted" : "Deleting medical record has failed";
    }
}
