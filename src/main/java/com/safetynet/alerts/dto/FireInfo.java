package com.safetynet.alerts.dto;

import lombok.Data;

import java.util.List;

@Data
public class FireInfo {

    private List<PersonMedicalInfo> persons;
    private int firestation;

    public FireInfo(List<PersonMedicalInfo> persons, int firestation) {
        this.persons = persons;
        this.firestation = firestation;
    }
}
