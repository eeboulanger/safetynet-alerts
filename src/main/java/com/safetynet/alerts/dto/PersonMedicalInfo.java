package com.safetynet.alerts.dto;

import lombok.Data;

import java.util.List;

@Data
public class PersonMedicalInfo {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int age;
    private List<String> medications;
    private List<String> allergies;

    public PersonMedicalInfo(String firstName, String lastName, String phoneNumber, int age, List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }
}
