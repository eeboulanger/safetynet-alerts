package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonInfoController {
    @Autowired
    private ICommunityService communityService;

    @GetMapping("/personInfo")
    public List<?> findAllHouseHoldsCoveredByStations(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        return communityService.getAllPersonsByName(firstName, lastName);
    }
}
