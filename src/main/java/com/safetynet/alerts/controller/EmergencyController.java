package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.dto.FireStationCoverageDTO;
import com.safetynet.alerts.dto.FloodDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.service.*;
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
    private final IFireAndFloodService fireAndFloodService;
    private final ICommunityService communityService;
    private final IEmailService emailService;

    @Autowired
    public EmergencyController(IFireStationCoverageService<?> coverageService, IChildAlertService<?> childAlertService, IPhoneAlertService phoneAlertService, IFireAndFloodService fireAndFloodService, ICommunityService communityService, IEmailService emailService) {
        this.coverageService = coverageService;
        this.childAlertService = childAlertService;
        this.phoneAlertService = phoneAlertService;
        this.fireAndFloodService = fireAndFloodService;
        this.communityService = communityService;
        this.emailService = emailService;
    }

    @GetMapping("/firestation")
    public ResponseEntity<?> getFireStationCoverage(@RequestParam("stationNumber") int stationNumber) {
        FireStationCoverageDTO coverage = (FireStationCoverageDTO) coverageService.findPersonsCoveredByFireStation(stationNumber);
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

    @GetMapping("/fire")
    public ResponseEntity<?> getListOfPersonsAndFireStationNumber(@RequestParam("address") String address) {
        FireDTO fireDTO = fireAndFloodService.findPersonsAndFireStation(address);
        return ResponseEntity.ok(fireDTO);
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<?> findAllHouseHoldsCoveredByStations(@RequestParam("stations") List<Integer> listOfFireStations) {
        List<FloodDTO> floodDTOList = fireAndFloodService.findAllHouseHoldsCoveredByStations(listOfFireStations);
        return ResponseEntity.ok(floodDTOList);
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<?> findAllHouseHoldsCoveredByStations(@RequestParam("city") String city) {
        Set<String> emails = emailService.getAllEmails(city);
        return ResponseEntity.ok(emails);
    }

    @GetMapping("/personInfo")
    public ResponseEntity<?> findAllHouseHoldsCoveredByStations(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        List<PersonInfoDTO> personInfo = communityService.getAllPersonsByName(firstName, lastName);
        return ResponseEntity.ok(personInfo);
    }
}
