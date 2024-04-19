package com.safetynet.alerts.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * Reads data from json file and convert to java objects
 */
public class JsonDataReaderFromFile implements IJsonDataReader{

    private static final String jsonPath = "data.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public <T> Optional<List<T>> findAll(String model, TypeReference<List<T>> typeReference) {

        try (InputStream inputStream = JsonDataReaderFromFile.class.getClassLoader().getResourceAsStream(jsonPath)) {
            JsonNode root = mapper.readTree(inputStream);
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