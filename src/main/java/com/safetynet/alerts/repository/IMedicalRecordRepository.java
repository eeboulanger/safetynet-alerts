package com.safetynet.alerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.safetynet.alerts.model.MedicalRecord;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Any class that handles queries to read and edit medical record data
 */

public interface IMedicalRecordRepository {
    Optional<List<MedicalRecord>> findAll();

    boolean delete(Map<String, String> identifier);

    boolean create(MedicalRecord medicalRecord);

    boolean update(MedicalRecord medicalRecord);

    Optional<MedicalRecord> findByName(String firstName, String lastName);
}
