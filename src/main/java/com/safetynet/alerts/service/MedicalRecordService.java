package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class MedicalRecordService implements IMedicalRecordService {

    @Autowired
    private MedicalRecordRepository recordRepository;

    @Override
    public Optional<List<MedicalRecord>> findAll() {
        return recordRepository.findAll();
    }

    @Override
    public Optional<MedicalRecord> findByName(String firstName, String lastName) {
        return recordRepository.findByName(firstName, lastName);
    }

    @Override
    public boolean create(MedicalRecord record) {
        return recordRepository.create(record);
    }

    @Override
    public boolean update(MedicalRecord record) {
        return recordRepository.update(record);
    }

    @Override
    public boolean delete(Map<String, String> recordId) {
        return recordRepository.delete(recordId);
    }
}
