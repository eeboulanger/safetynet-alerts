package com.safetynet.alerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.util.IJsonDataReader;
import com.safetynet.alerts.util.JsonDataReaderFromFile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class MedicalRecordRepository implements DataRepository<MedicalRecord> {

    IJsonDataReader reader = new JsonDataReaderFromFile();

    @Override
    public Optional<List<MedicalRecord>> findAll() {
        return reader.findAll("medicalrecords", new TypeReference<>() {
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

    public Optional<MedicalRecord> findByName(String firstName, String lastName) {
        return this.findAll().flatMap(list -> list.stream()
                .filter(f -> Objects.equals(f.getFirstName(), firstName) && Objects.equals(f.getLastName(), lastName))
                .findFirst());
    }
}
