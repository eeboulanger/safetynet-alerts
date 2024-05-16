package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.FireStation;

import java.util.List;
import java.util.Optional;

/**
 * Any class that handled queries to read and edit Fire station data
 */
public interface IFireStationRepository extends ICrudRepository<FireStation> {
    Optional<List<FireStation>> findByStationNumber(int number);

    Optional<FireStation> findStationByAddress(String address);
}
