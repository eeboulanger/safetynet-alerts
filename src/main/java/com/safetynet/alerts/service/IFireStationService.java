package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;

import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * Any class that handles searching and editing fire station data
 *
 */
public interface IFireStationService {

    boolean create(FireStation fireStation);

    boolean update(FireStation fireStation);

    boolean delete(Map<String, String> stationId);

    Optional<List<FireStation>> findByStationNumber(int stationNumber);

    Optional<FireStation> findStationByAddress(String address);
}
