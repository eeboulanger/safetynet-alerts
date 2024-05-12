package com.safetynet.alerts.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataContainer;
import com.safetynet.alerts.model.MedicalRecord;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MedicalRecordJsonDataEditor implements IJsonDataEditor<MedicalRecord> {

    private static final String JSON_DATA_PATH = "./data/data.json";
    private final File jsonFile = new File(JSON_DATA_PATH);
    private final ObjectMapper mapper = new ObjectMapper();
    private DataContainer data = new DataContainer();
    private List<MedicalRecord> medicalRecordList;

    public MedicalRecordJsonDataEditor() {
        try {
            if (jsonFile.exists()) {
                data = mapper.readValue(jsonFile, DataContainer.class);
                medicalRecordList = data.getMedicalrecords();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean create(MedicalRecord record) {
        try {
            if (medicalRecordList != null) {
                //check if medical record already exists
                for (MedicalRecord medicalRecord : medicalRecordList) {
                    if (record.getFirstName().equals(medicalRecord.getFirstName())
                            && record.getLastName().equals(medicalRecord.getLastName())) {
                        return false;
                    }
                }
                medicalRecordList.add(record);
                saveData();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(MedicalRecord record) {
        if (medicalRecordList != null) {
            try {
                for (int i = 0; i < medicalRecordList.size(); i++) {
                    if (record.getFirstName().equals(medicalRecordList.get(i).getFirstName())
                            && record.getLastName().equals(medicalRecordList.get(i).getLastName())) {
                        medicalRecordList.set(i, record);
                        saveData();
                        return true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean delete(Map<String, String> identifier) {
        if (medicalRecordList != null) {
            try {
                boolean isDeleted = medicalRecordList.removeIf(record -> record.getFirstName().equals(identifier.get("firstname"))
                        && record.getLastName().equals(identifier.get("lastname")));
                if (isDeleted) {
                    saveData();
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void saveData() throws IOException {
        if (medicalRecordList != null) {
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, data);
        }
    }
}
