package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.dto.FloodDTO;

import java.util.List;

/**
 * Any class that handles search for persons covered by fire stations
 */
public interface IFireAndFloodService {

    /**
     * Finds persons and their fire station and converts to DTO
     *
     * @param address used for searching
     * @return a DTO with the information
     */
    FireDTO findPersonsAndFireStation(String address);

    /**
     * Finds covered persons by their fire station
     *
     * @param fireStationNumbers is the list of fire stations used for searching persons
     * @return a list of fire station numbers and addresses and covered persons for each address
     */
    List<FloodDTO> findAllHouseHoldsCoveredByStations(List<Integer> fireStationNumbers);
}
