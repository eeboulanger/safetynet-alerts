package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IMedicalRecordService {

    Optional<List<MedicalRecord>> findAll();

    Optional<MedicalRecord> findByName(String firstname, String lastname);

    boolean create(MedicalRecord record);

    boolean update(MedicalRecord record);

    boolean delete(Map<String, String> recordId);
}
