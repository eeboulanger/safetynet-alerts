package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
@Primary
public class MedicalRecordRepository implements IMedicalRecordRepository {
    private List<MedicalRecord> medicalRecordList;
    private static final Logger logger = LogManager.getLogger(MedicalRecordRepository.class);

    public boolean saveAll(List<MedicalRecord> medicalRecordList) {
        this.medicalRecordList = medicalRecordList;
        return medicalRecordList != null;
    }

    @Override
    public boolean create(MedicalRecord record) {
        boolean isCreated = false;
        if (medicalRecordList != null) {
            //check if medical record already exists
            for (MedicalRecord medicalRecord : medicalRecordList) {
                if (record.getFirstName().equals(medicalRecord.getFirstName())
                        && record.getLastName().equals(medicalRecord.getLastName())) {
                    logger.error("Failed to create new medical record, there's already a record with the same name");
                    return false;
                }
            }
            medicalRecordList.add(record);
            isCreated = true;
        } else {
            logger.error("Failed to create new medical record. Medical record list doesn't exist.");
        }
        return isCreated;
    }

    @Override
    public boolean update(MedicalRecord record) {
        boolean isUpdated = false;
        if (medicalRecordList != null) {
            for (int i = 0; i < medicalRecordList.size(); i++) {
                if (record.getFirstName().equals(medicalRecordList.get(i).getFirstName())
                        && record.getLastName().equals(medicalRecordList.get(i).getLastName())) {
                    medicalRecordList.set(i, record);
                    isUpdated = true;
                    break;
                }
            }
            if (!isUpdated) {
                logger.error("Failed to update the medical record. No record with the given name exists.");
            }
        } else {
            logger.error("Failed to update medical record, there is no list of medical records");
        }
        return isUpdated;
    }

    @Override
    public boolean delete(Map<String, String> identifier) {
        boolean isDeleted = false;
        if (medicalRecordList != null) {
            isDeleted = medicalRecordList.removeIf(record -> record.getFirstName().equals(identifier.get("firstName"))
                    && record.getLastName().equals(identifier.get("lastName")));
            if (!isDeleted) {
                logger.error("Failed to delete medical record, there is no record with the given name");
            }
        } else {
            logger.error("Failed to delete medical record, there is no list of medical records");
        }
        return isDeleted;
    }

    @Override
    public Optional<List<MedicalRecord>> findAll() {
        if (medicalRecordList == null) {
            logger.error("There's no list of medical records");
            return Optional.empty();
        }
        return Optional.of(medicalRecordList);
    }

    @Override
    public Optional<MedicalRecord> findByName(String firstName, String lastName) {
        if(medicalRecordList == null){
            logger.error("Medical record list doesn't exist.");
            return Optional.empty();
        }
        return medicalRecordList.stream()
                .filter(f -> Objects.equals(f.getFirstName(), firstName)
                        && Objects.equals(f.getLastName(), lastName))
                .findFirst();
    }
}
