package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FireStationCoverage;
import com.safetynet.alerts.service.FireStationCoverageService;
import com.safetynet.alerts.service.IFireStationCoverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmergencyController {

    private final IFireStationCoverageService<FireStationCoverage> coverageService;

    @Autowired
    public EmergencyController(FireStationCoverageService fireStationCoverageService) {
        this.coverageService = fireStationCoverageService;
    }

    @GetMapping("/firestation")
    public ResponseEntity<?> getFireStationCoverage(@RequestParam("stationNumber") int stationNumber) {
        FireStationCoverage coverage = (FireStationCoverage) coverageService.findPersonsCoveredByFireStation(stationNumber);
            return ResponseEntity.ok(coverage);
    }

}
