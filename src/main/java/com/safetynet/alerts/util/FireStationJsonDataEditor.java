package com.safetynet.alerts.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataContainer;
import com.safetynet.alerts.model.FireStation;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FireStationJsonDataEditor implements IJsonDataEditor<FireStation> {

    private static final String JSON_DATA_PATH = "./data/data.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final File jsonFile = new File(JSON_DATA_PATH);
    private DataContainer data;
    private List<FireStation> fireStationList;

    public FireStationJsonDataEditor() {
        try {
            if (jsonFile.exists()) {
                data = mapper.readValue(jsonFile, DataContainer.class);
                fireStationList = data.getFirestations();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean create(FireStation station) {
        try {
            for (FireStation fireStation : fireStationList) {
                if (station.getAddress().equals(fireStation.getAddress())
                        && station.getStation() == fireStation.getStation()) {
                    return false;
                }
            }
            fireStationList.add(station);
            saveData();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates station number for an address
     *
     * @param station is the station updated with the new number
     * @return true if successfully updated
     */
    @Override
    public boolean update(FireStation station) {
        boolean isUpdated = false;
        try {
            for (int i = 0; i < fireStationList.size(); i++) {
                if (station.getAddress().equals(fireStationList.get(i).getAddress())) {
                    fireStationList.set(i, station);
                    isUpdated = true;
                    break;
                }
            }
            if (isUpdated) {
                saveData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    @Override
    public boolean delete(Map<String, String> identifier) {
        boolean isDeleted = false;
        try {
            isDeleted = fireStationList.removeIf(s -> String.valueOf(s.getStation()).equals(identifier.get("station"))
                    && s.getAddress().equals(identifier.get("address")));
            if (isDeleted) {
                saveData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    private void saveData() throws IOException {
        if (fireStationList != null) {
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, data);
        }
    }
}
