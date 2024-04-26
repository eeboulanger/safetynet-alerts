package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireInfo;
import com.safetynet.alerts.dto.FloodInfo;
import com.safetynet.alerts.dto.PersonMedicalInfo;

import java.util.List;

/**
 * Finds all covered persons and fire station by address
 */
public interface IFireService {

    /**
     * Finds persons and firestation and converts to DTO
     *
     * @param address used for searching
     * @return a DTO with the information
     */
    FireInfo findPersonsAndFireStation(String address);
    List<FloodInfo> findAllHouseHoldsCoveredByStations(List<Integer> fireStationNumbers);
}
