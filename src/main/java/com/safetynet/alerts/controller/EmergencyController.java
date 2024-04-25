package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FireStationCoverage;
import com.safetynet.alerts.service.IChildAlertService;
import com.safetynet.alerts.service.IFireStationCoverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmergencyController {

    private final IFireStationCoverageService<?> coverageService;

    private final IChildAlertService<?> childAlertService;

    @Autowired
    public EmergencyController(IFireStationCoverageService<?> coverageService, IChildAlertService<?> childAlertService) {
        this.coverageService = coverageService;
        this.childAlertService = childAlertService;
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

}
