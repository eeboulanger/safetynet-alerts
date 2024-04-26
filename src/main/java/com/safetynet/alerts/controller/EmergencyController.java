package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FireInfo;
import com.safetynet.alerts.dto.FireStationCoverage;
import com.safetynet.alerts.dto.FloodInfo;
import com.safetynet.alerts.service.IChildAlertService;
import com.safetynet.alerts.service.IFireAndFloodService;
import com.safetynet.alerts.service.IFireStationCoverageService;
import com.safetynet.alerts.service.IPhoneAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class EmergencyController {

    private final IFireStationCoverageService<?> coverageService;
    private final IChildAlertService<?> childAlertService;
    private final IPhoneAlertService phoneAlertService;
    private final IFireAndFloodService fireAndFloodService;

    @Autowired
    public EmergencyController(IFireStationCoverageService<?> coverageService, IChildAlertService<?> childAlertService, IPhoneAlertService phoneAlertService, IFireAndFloodService fireAndFloodService) {
        this.coverageService = coverageService;
        this.childAlertService = childAlertService;
        this.phoneAlertService = phoneAlertService;
        this.fireAndFloodService = fireAndFloodService;
    }

    @GetMapping("/firestation")
    public ResponseEntity<?> getFireStationCoverage(@RequestParam("stationNumber") int stationNumber) {
        FireStationCoverage coverage = (FireStationCoverage) coverageService.findPersonsCoveredByFireStation(stationNumber);
        return ResponseEntity.ok(coverage);
    }

    @GetMapping("/childAlert")
    public ResponseEntity<?> getListOfChildren(@RequestParam("address") String address) {
        List<?> children = childAlertService.findAllChildren(address);
        return ResponseEntity.ok(children);
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<?> getListOfPhoneNumbers(@RequestParam("firestation") int station_number) {
        Set<String> phoneNumbers = phoneAlertService.findPhoneNumbersByFireStation(station_number);
        return ResponseEntity.ok(phoneNumbers);
    }

    @GetMapping("/fire")
    public ResponseEntity<?> getListOfPersonsAndFireStationNumber(@RequestParam("address") String address) {
        FireInfo fireInfo = fireAndFloodService.findPersonsAndFireStation(address);
        return ResponseEntity.ok(fireInfo);
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<?> findAllHouseHoldsCoveredByStations(@RequestParam("stations") List<Integer> listOfFireStations) {
        List<FloodInfo> floodInfoList = fireAndFloodService.findAllHouseHoldsCoveredByStations(listOfFireStations);
        return ResponseEntity.ok(floodInfoList);
    }

}
