package com.safetynet.alerts.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FireStationCoverage {

    List<PersonInfo> personList;
    Map<String, Integer> count;

    public FireStationCoverage(List<PersonInfo> personList, Map<String, Integer> count) {
        this.personList = personList;
        this.count = count;
    }


}
