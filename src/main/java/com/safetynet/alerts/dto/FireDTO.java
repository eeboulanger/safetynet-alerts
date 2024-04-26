package com.safetynet.alerts.dto;

import lombok.Data;

import java.util.List;

@Data
public class FireDTO {

    private List<PersonMedicalInfo> persons;
    private int firestation;

    public FireDTO(List<PersonMedicalInfo> persons, int firestation) {
        this.persons = persons;
        this.firestation = firestation;
    }
}
