package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonInfoController {
    @Autowired
    private ICommunityService communityService;

    @GetMapping("/personInfo")
    public ResponseEntity<?> findAllHouseHoldsCoveredByStations(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        List<PersonInfoDTO> personInfo = communityService.getAllPersonsByName(firstName, lastName);
        return ResponseEntity.ok(personInfo);
    }
}
