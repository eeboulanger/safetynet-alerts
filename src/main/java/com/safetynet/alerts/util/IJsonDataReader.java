package com.safetynet.alerts.util;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Optional;

public interface IJsonDataReader {

      <T> Optional<List<T>> findAll(String model, TypeReference<List<T>> typeReference);
}
