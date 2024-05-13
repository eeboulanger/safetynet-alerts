package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.IPhoneAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class PhoneAlertController {
    @Autowired
    private IPhoneAlertService phoneAlertService;

    @GetMapping("/phoneAlert")
    public Set<String> getListOfPhoneNumbers(@RequestParam("firestation") int station_number) {
        return phoneAlertService.findPhoneNumbersByFireStation(station_number);
    }
}
