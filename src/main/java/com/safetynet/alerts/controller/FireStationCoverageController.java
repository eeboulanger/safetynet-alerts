package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FireStationCoverageDTO;
import com.safetynet.alerts.service.IFireStationCoverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireStationCoverageController {

    @Autowired
    private IFireStationCoverageService<?> coverageService;

    @GetMapping("/firestation")
    public Object getFireStationCoverage(@RequestParam("stationNumber") int stationNumber) {
        return coverageService.findPersonsCoveredByFireStation(stationNumber);
    }
}
