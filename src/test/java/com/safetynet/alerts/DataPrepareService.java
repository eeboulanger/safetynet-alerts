package com.safetynet.alerts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataContainer;
import com.safetynet.alerts.model.Person;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    public void resetData() throws IOException {
        if (originalData != null) {
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, originalData);
        }
    }
}
