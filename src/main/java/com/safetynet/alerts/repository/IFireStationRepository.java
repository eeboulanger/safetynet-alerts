package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.FireStation;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Any class that handled queries to read and edit Fire station data
 */
public interface IFireStationRepository {

    boolean create(FireStation fireStation);

    boolean update(FireStation fireStation);

    boolean delete(Map<String, String> identifier);

    Optional<List<FireStation>> findAll();

    Optional<List<FireStation>> findByStationNumber(int number);

    Optional<FireStation> findStationByAddress(String address);
}
