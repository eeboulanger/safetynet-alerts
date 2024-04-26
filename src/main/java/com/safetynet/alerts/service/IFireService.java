package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireInfo;

/**
 * Finds all covered persons and fire station by address
 */
public interface IFireService<T> {

    /**
     * Finds persons and firestation and converts to DTO
     *
     * @param address used for searching
     * @return a DTO with the information
     */
    T findPersonsAndFireStation(String address);
}
