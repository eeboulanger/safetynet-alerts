package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStationCoverage;

public interface IEmergencyService {

     /**
      * Finds all persons covered by a fire station
      * @param stationNumber
      * @return list of person info - firstname, lastname, address, phone number - and the count of children and adults
      */
     FireStationCoverage findPersonsCoveredByFireStation(int stationNumber);
}
