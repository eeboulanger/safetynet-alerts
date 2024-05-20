package com.safetynet.alerts.service;

import java.util.Set;

/**
 * Any class that handles finding phone numbers for homes covered by station
 */
public interface IPhoneAlertService {

    Set<String> findPhoneNumbersByFireStation(int stationNumber);
}
