package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.IChildAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChildAlertController {

    @Autowired
    private IChildAlertService<?> childAlertService;

    @GetMapping("/childAlert")
    public List<?> getListOfChildren(@RequestParam("address") String address) {
        return childAlertService.findAllChildren(address);
    }
}
