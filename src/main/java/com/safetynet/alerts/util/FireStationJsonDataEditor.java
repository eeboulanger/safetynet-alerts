package com.safetynet.alerts.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataContainer;
import com.safetynet.alerts.model.FireStation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FireStationJsonDataEditor implements IJsonDataEditor<FireStation> {
    private static final Logger logger = LogManager.getLogger(FireStationJsonDataEditor.class);
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
            logger.error("Failed to initialize fire station editor: " + e);
        }
    }

    @Override
    public boolean create(FireStation station) {
        if (fireStationList != null) {
            try {
                for (FireStation fireStation : fireStationList) {
                    if (station.getAddress().equals(fireStation.getAddress())
                            && station.getStation() == fireStation.getStation()) {
                        logger.debug("Failed to create new fire station. " +
                                "There's already a fire station wih the same address and station number.");
                        return false;
                    }
                }
                fireStationList.add(station);
                saveData();
                return true;
            } catch (Exception e) {
                logger.error("Failed to create new fire station: " + e);
                return false;
            }
        }
        logger.debug("Failed to create new fire station. There's no fire station list.");
        return false;
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
        if (fireStationList != null) {
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
                } else {
                    logger.debug("Failed to update fire station. " +
                            "There's no fire station covering the given address");
                }
            } catch (Exception e) {
                logger.error("Failed to update fire station: " + e);
            }
        }
        logger.debug("Failed to update fire station. There's no list of fire stations.");
        return isUpdated;
    }

    @Override
    public boolean delete(Map<String, String> identifier) {
        boolean isDeleted = false;
        if (fireStationList != null) {
            try {
                isDeleted = fireStationList.removeIf(s -> String.valueOf(s.getStation()).equals(identifier.get("station"))
                        && s.getAddress().equals(identifier.get("address")));
                if (isDeleted) {
                    saveData();
                } else {
                    logger.debug("Failed to delete fire station. " +
                            "There's no fire station with the given address and station number.");
                }
            } catch (Exception e) {
                logger.error("Failed to delete fire station: " + e);
            }
        }
        logger.debug("Failed to delete fire station. There is no list of fire stations.");
        return isDeleted;
    }

    private void saveData() throws IOException {
        if (fireStationList != null) {
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, data);
        }
    }
}
