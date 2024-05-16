package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.IFireAndFloodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FloodController {
    @Autowired
    private IFireAndFloodService fireAndFloodService;

    @GetMapping("/flood/stations")
    public List<?> findAllHouseHoldsCoveredByStations(@RequestParam("stations") List<Integer> listOfFireStations) {
        return fireAndFloodService.findAllHouseHoldsCoveredByStations(listOfFireStations);
    }
}
