package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.IFireAndFloodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireController {
    @Autowired
    private IFireAndFloodService fireAndFloodService;

    @GetMapping("/fire")
    public Object getListOfPersonsAndFireStationNumber(@RequestParam("address") String address) {
        return fireAndFloodService.findPersonsAndFireStation(address);
    }
}
