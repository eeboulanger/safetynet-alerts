package com.safetynet.alerts.dto;

import lombok.Data;

import java.util.List;

@Data
public class FloodInfo {

    private int firestationNumber;
    private String address;
    private List<PersonMedicalInfo> medicalInfoList;

    public FloodInfo(int firestationNumber, String address, List<PersonMedicalInfo> medicalInfoList) {
        this.firestationNumber = firestationNumber;
        this.address = address;
        this.medicalInfoList = medicalInfoList;
    }
}
