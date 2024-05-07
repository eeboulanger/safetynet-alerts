package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class PersonController {

    @Autowired
    PersonService personService;

    @DeleteMapping("/person")
    public String deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        boolean isDeleted = personService.delete(Map.of(
                "firstName", firstName,
                "lastName", lastName
        ));
        if (isDeleted) {
            return "Person has been successfully deleted";
        } else {
            return "Failed to delete person with the given name";
        }
    }
}
