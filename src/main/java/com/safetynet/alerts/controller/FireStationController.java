package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class FireStationController {
    @Autowired
    private FireStationService fireStationService;

    @PostMapping("/firestation")
    public String createFireStation(@RequestBody FireStation fireStation) {
        return fireStationService.create(fireStation) ? "Firestation has been successfully created" : "Creating firestation failed";
    }

    @PutMapping("/firestation")
    public String updateFireStation(@RequestBody FireStation fireStation) {
        return fireStationService.update(fireStation) ? "Firestation has been successfully updated" : "Updating firestation failed";
    }

    @DeleteMapping("/firestation")
    public String deleteFireStation(@RequestBody FireStation fireStation) {
        return fireStationService.delete(Map.of(
                        "address", fireStation.getAddress(),
                        "station", String.valueOf(fireStation.getStation())
                )
        ) ? "Firestation has been successfully deleted" : "Deleting firestation failed";
    }
}
