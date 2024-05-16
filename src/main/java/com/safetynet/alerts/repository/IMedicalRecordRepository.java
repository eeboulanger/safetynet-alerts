package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;

import java.util.Optional;

/**
 * Any class that handles queries to read and edit medical record data
 */

public interface IMedicalRecordRepository extends ICrudRepository<MedicalRecord> {

    Optional<MedicalRecord> findByName(String firstName, String lastName);
}
