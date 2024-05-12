package com.safetynet.alerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.util.IJsonDataEditor;
import com.safetynet.alerts.util.IJsonDataReader;
import com.safetynet.alerts.util.JsonDataReaderFromFile;
import com.safetynet.alerts.util.MedicalRecordJsonDataEditor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class MedicalRecordRepository implements DataRepository<MedicalRecord> {

    private final IJsonDataReader reader = new JsonDataReaderFromFile();
    private final IJsonDataEditor<MedicalRecord> editor = new MedicalRecordJsonDataEditor();

    @Override
    public Optional<List<MedicalRecord>> findAll() {
        return reader.findAll("medicalrecords", new TypeReference<>() {
        });
    }

    @Override
    public boolean delete(Map<String, String> identifier) {
        return editor.delete(identifier);
    }

    @Override
    public boolean create(MedicalRecord medicalRecord) {
        return editor.create(medicalRecord);
    }

    @Override
    public boolean update(MedicalRecord medicalRecord) {
        return editor.update(medicalRecord);
    }

    public Optional<MedicalRecord> findByName(String firstName, String lastName) {
        return this.findAll().flatMap(list -> list.stream()
                .filter(f -> Objects.equals(f.getFirstName(), firstName) && Objects.equals(f.getLastName(), lastName))
                .findFirst());
    }
}
