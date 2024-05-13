package com.safetynet.alerts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataContainer;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DataPrepareService {

    private static final String JSON_DATA_PATH = "./data/data.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final File jsonFile = new File(JSON_DATA_PATH);
    private DataContainer originalData;
    private DataContainer currentData;

    public DataPrepareService() {
        try {
            if (jsonFile.exists()) {
                originalData = mapper.readValue(jsonFile, DataContainer.class);
                currentData = mapper.readValue(jsonFile, DataContainer.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Person getPerson(int i) {
        return currentData.getPersons().get(i);
    }

    public List<Person> getPersonsList() {
        return currentData.getPersons();
    }

    public List<FireStation> getFireStationsList() {
        return currentData.getFirestations();
    }

    public FireStation getFireStation(int i) {
        return currentData.getFirestations().get(i);
    }

    public MedicalRecord getMedicalRecord(int i) {
        return currentData.getMedicalrecords().get(i);
    }

    public List<MedicalRecord> getMedicalRecordsList() {
        return currentData.getMedicalrecords();
    }

    public void resetData() throws IOException {
        if (originalData != null) {
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, originalData);
        }
    }

    public DataContainer getData() throws IOException {
        return mapper.readValue(jsonFile, DataContainer.class);
    }
}