package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class EmailController {
    @Autowired
    private IEmailService emailService;

    @GetMapping("/communityEmail")
    public Set<String> findAllHouseHoldsCoveredByStations(@RequestParam("city") String city) {
        return emailService.getAllEmails(city);
    }
}
