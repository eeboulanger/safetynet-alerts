package com.safetynet.alerts.dto;

import lombok.Data;

import java.util.List;

@Data
public class PersonInfoDTO {
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private List<String> medications;
    private List<String> allergies;

    public PersonInfoDTO(String firstName, String lastName, String email, int age, List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }
}
