package com.safetynet.alerts.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChildInfo {

    private String firstName;
    private String lastName;
    private int age;
    private List<PersonInfo> familyMemberList;

    public ChildInfo(String firstName, String lastName, int age, List<PersonInfo> familyMemberList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.familyMemberList = familyMemberList;
    }
}
