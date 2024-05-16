package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;

import java.util.Optional;

/**
 * Any service that handles medical record crud queries
 */

public interface IMedicalRecordService extends ICrudService<MedicalRecord> {

    Optional<MedicalRecord> findByName(String firstname, String lastname);

}
