package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireInfo;
import com.safetynet.alerts.dto.FloodInfo;

import java.util.List;

/**
 * Finds covered persons and fire stations
 */
public interface IFireAndFloodService {

    /**
     * Finds persons and fire station and converts to DTO
     *
     * @param address used for searching
     * @return a DTO with the information
     */
    FireInfo findPersonsAndFireStation(String address);

    /**
     * Finds covered persons and firestation number
     *
     * @param fireStationNumbers is the list of fire stations used for searching persons
     * @return a list of fire station numbers and adresses and covered persons for each address
     */
    List<FloodInfo> findAllHouseHoldsCoveredByStations(List<Integer> fireStationNumbers);
}
