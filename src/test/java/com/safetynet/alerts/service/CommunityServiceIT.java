package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonInfoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CommunityServiceIT {

    @Autowired
    private CommunityService communityService;

    @Test
    @DisplayName("Given there are persons with name then return list")
    public void getAllPersonsByNameTest() {

        List<PersonInfoDTO> result = communityService.getAllPersonsByName("John", "Boyd");

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Boyd", result.get(0).getLastName());
        assertEquals(List.of("aznol:350mg", "hydrapermazol:100mg"), result.get(0).getMedications());
        assertEquals(List.of("nillacilan"), result.get(0).getAllergies());
        assertEquals("jaboyd@email.com", result.get(0).getEmail());
    }

    @Test
    @DisplayName("Given there are no persons with name then return empty list")
    public void getAllPersonsByNameTest_whenNoPerson_returnsEmptyList() {

        List<PersonInfoDTO> result = communityService.getAllPersonsByName("No", "Name");

        assertEquals(0, result.size());
    }


}
