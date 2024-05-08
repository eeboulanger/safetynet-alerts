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
        return isCreated ? "Person has been successfully saved" : "Saving person has failed";
    }

    @PutMapping("/person")
    public String updatePerson(@RequestBody Person person) {
        boolean isUpdated = personService.update(person);
        return isUpdated ? "Person has been successfully updated" : "Updating person has failed";
    }

    @DeleteMapping("/person")
    public String deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        boolean isDeleted = personService.delete(Map.of(
                "firstName", firstName,
                "lastName", lastName
        ));
        return isDeleted ? "Person has been successfully deleted" : "Failed to delete person with the given name";

    }
}
