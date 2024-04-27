package com.safetynet.alerts.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChildDTO {

    private String firstName;
    private String lastName;
    private int age;
    private List<PersonContactInfo> familyMemberList;

    public ChildDTO(String firstName, String lastName, int age, List<PersonContactInfo> familyMemberList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.familyMemberList = familyMemberList;
    }
}
