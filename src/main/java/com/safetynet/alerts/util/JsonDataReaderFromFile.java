package com.safetynet.alerts.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Reads data from json file and convert to java objects
 */
@Component
public class JsonDataReaderFromFile implements IJsonDataReader {
    private static final Logger logger = LogManager.getLogger(JsonDataReaderFromFile.class);
    private static final String JSON_DATA_PATH = "./data/data.json";
    private final ObjectMapper mapper;

    @Autowired
    public JsonDataReaderFromFile(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> Optional<List<T>> findAll(String model, TypeReference<List<T>> typeReference) {

        try {
            File jsonFile = new File(JSON_DATA_PATH);
            JsonNode root = mapper.readTree(jsonFile);
            JsonNode node = root.get(model);
            logger.debug("Reading data from file: " + JSON_DATA_PATH);

            if (node == null || node.isEmpty()) {
                logger.debug("Data model: " + model + " doesn't exist in file");
                return Optional.empty();
            }

            List<T> result = mapper.convertValue(node, typeReference);
            logger.debug("Found list of data model: " + model + ", list: " + result);
            return Optional.of(result);
        } catch (IOException e) {
            logger.error("Failed to read from file: " + e);
            return Optional.empty();
        }
    }
}
