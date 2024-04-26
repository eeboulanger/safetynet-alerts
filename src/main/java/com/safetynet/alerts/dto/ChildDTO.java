package com.safetynet.alerts.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChildDTO {

    private String firstName;
    private String lastName;
    private int age;
    private List<PersonInfo> familyMemberList;

    public ChildDTO(String firstName, String lastName, int age, List<PersonInfo> familyMemberList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.familyMemberList = familyMemberList;
    }
}
