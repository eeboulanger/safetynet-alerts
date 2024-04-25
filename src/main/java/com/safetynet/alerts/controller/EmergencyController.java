package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FireStationCoverage;
import com.safetynet.alerts.service.IChildAlertService;
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

    @Autowired
    public EmergencyController(IFireStationCoverageService<?> coverageService, IChildAlertService<?> childAlertService, IPhoneAlertService phoneAlertService) {
        this.coverageService = coverageService;
        this.childAlertService = childAlertService;
        this.phoneAlertService = phoneAlertService;
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

}
