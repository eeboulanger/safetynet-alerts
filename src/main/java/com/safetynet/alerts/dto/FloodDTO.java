package com.safetynet.alerts.dto;

import lombok.Data;

import java.util.List;

@Data
public class FloodDTO {

    private int firestationNumber;
    private String address;
    private List<PersonMedicalInfo> medicalInfoList;

    public FloodDTO(int firestationNumber, String address, List<PersonMedicalInfo> medicalInfoList) {
        this.firestationNumber = firestationNumber;
        this.address = address;
        this.medicalInfoList = medicalInfoList;
    }
}
