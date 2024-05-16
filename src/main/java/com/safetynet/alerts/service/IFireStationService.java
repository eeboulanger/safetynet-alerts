package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;

import java.util.List;
import java.util.Optional;
/**
 * Any class that handles searching and editing fire station data
 *
 */
public interface IFireStationService extends ICrudService<FireStation>{

    Optional<List<FireStation>> findByStationNumber(int stationNumber);

    Optional<FireStation> findStationByAddress(String address);
}
