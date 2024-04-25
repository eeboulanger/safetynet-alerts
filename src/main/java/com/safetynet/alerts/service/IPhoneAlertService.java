package com.safetynet.alerts.service;

import java.util.Set;

/**
 * Find phone numbers for homes covered by station
 */
public interface IPhoneAlertService {

    Set<String> findPhoneNumbersByFireStation(int stationNumber);
}
