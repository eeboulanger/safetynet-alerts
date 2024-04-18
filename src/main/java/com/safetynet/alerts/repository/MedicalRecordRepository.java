package com.safetynet.alerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.util.JsonDataReader;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class MedicalRecordRepository implements DataRepository<MedicalRecord> {
    @Override
    public Optional<List<MedicalRecord>> findAll() {
        return JsonDataReader.findAll("medicalrecords", new TypeReference<List<MedicalRecord>>() {
        });
    }

    @Override
    public void delete(MedicalRecord entity) {

    }

    @Override
    public void create(MedicalRecord entity) {

    }

    @Override
    public void update(MedicalRecord entity) {

    }
}
