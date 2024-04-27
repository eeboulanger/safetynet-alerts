package com.safetynet.alerts.dto;

import lombok.Data;

@Data
public class PersonContactInfo {

    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;

    public PersonContactInfo(String firstName, String lastName, String address, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

}
