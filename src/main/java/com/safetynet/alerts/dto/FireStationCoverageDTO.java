package com.safetynet.alerts.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FireStationCoverageDTO {

    List<PersonInfo> personList;
    Map<String, Integer> count;

    public FireStationCoverageDTO(List<PersonInfo> personList, Map<String, Integer> count) {
        this.personList = personList;
        this.count = count;
    }


}
