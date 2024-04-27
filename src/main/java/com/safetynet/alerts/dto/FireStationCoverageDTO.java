package com.safetynet.alerts.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FireStationCoverageDTO {

    List<PersonContactInfo> personList;
    Map<String, Integer> count;

    public FireStationCoverageDTO(List<PersonContactInfo> personList, Map<String, Integer> count) {
        this.personList = personList;
        this.count = count;
    }


}
