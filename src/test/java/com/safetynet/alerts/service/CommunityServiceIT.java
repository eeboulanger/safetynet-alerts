package com.safetynet.alerts.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CommunityServiceIT {

    @Autowired
    private CommunityService communityService;

    @Test
    @DisplayName("Given there are persons when entering city then return a list of emails")
    public void getAllEmails_whenPersons_thenReturnListOfEmails() {
        Set<String> result = communityService.getAllEmails("Culver");

        assertEquals(15, result.size());
    }

    @Test
    @DisplayName("Given there are no persons when entering the city then return empty list")
    public void getAllEmails_whenNoPersons_thenReturnEmptyList() {
        Set<String> result = communityService.getAllEmails("No city");

        assertTrue(result.isEmpty());
    }
}
