package com.safetynet.alerts.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * Reads data from json file and convert to java objects
 */
public class JsonDataReaderFromFile implements IJsonDataReader{

    private static final String JSON_DATA_PATH = "./data/data.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public <T> Optional<List<T>> findAll(String model, TypeReference<List<T>> typeReference) {

        try  {
            File jsonFile = new File(JSON_DATA_PATH);
            JsonNode root = mapper.readTree(jsonFile);
            JsonNode node = root.get(model);

            if (node == null || node.isEmpty()) {
                return Optional.empty();
            }

            List<T> result = mapper.convertValue(node, typeReference);
            return Optional.of(result);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
