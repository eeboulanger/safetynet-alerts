package com.safetynet.alerts.service;

import com.safetynet.alerts.config.DataInitializer;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.util.AgeCalculator;
import org.junit.jupiter.api.BeforeEach;
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
    @Autowired
    private DataInitializer dataInitializer;
    @BeforeEach
    public void setUp() {
        dataInitializer.run();
    }

    @Test
    @DisplayName("Given there are persons with name then return list")
    public void getAllPersonsByNameTest() {

        List<PersonInfoDTO> result = communityService.getAllPersonsByName("Tony", "Cooper");

        assertEquals(1, result.size());
        assertEquals("Tony", result.get(0).getFirstName());
        assertEquals("Cooper", result.get(0).getLastName());
        assertEquals(30, result.get(0).getAge());
        assertEquals(List.of("hydrapermazol:300mg", "dodoxadin:30mg"), result.get(0).getMedications());
        assertEquals(List.of("shellfish"), result.get(0).getAllergies());
        assertEquals("tcoop@ymail.com", result.get(0).getEmail());
    }

    @Test
    @DisplayName("Given there are no persons with name then return empty list")
    public void getAllPersonsByNameTest_whenNoPerson_returnsEmptyList() {

        List<PersonInfoDTO> result = communityService.getAllPersonsByName("No", "Name");

        assertEquals(0, result.size());
    }


}
