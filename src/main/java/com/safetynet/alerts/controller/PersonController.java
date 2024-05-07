package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class PersonController {

    @Autowired
    PersonService personService;

    @PostMapping("/person")
    public String createPerson(@RequestBody Person person) {
        boolean isCreated = personService.create(person);

        if (isCreated) {
            return "Person has been successfully saved";
        } else {
            return "Saving person has failed";
        }
    }

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
