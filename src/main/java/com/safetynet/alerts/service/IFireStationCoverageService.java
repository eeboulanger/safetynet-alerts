package com.safetynet.alerts.service;

public interface IFireStationCoverageService<T> {

     /**
      * Finds all persons covered by a fire station
      * @param stationNumber of the firestation
      * @return list of person info
      */
     T findPersonsCoveredByFireStation(int stationNumber);
}
