package com.safetynet.alerts.service;

import com.safetynet.alerts.config.DataInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class EmailServiceIT {

    @Autowired
    private EmailService emailService;
    @Autowired
    private DataInitializer dataInitializer;

    @BeforeEach
    public void setUp() {
        dataInitializer.run();
    }

    @Test
    @DisplayName("Given there are persons when entering city then return a list of emails")
    public void getAllEmails_whenPersons_thenReturnListOfEmails() {
        Set<String> result = emailService.getAllEmails("Culver");

        assertEquals(15, result.size());
    }

    @Test
    @DisplayName("Given there are no persons when entering the city then return empty list")
    public void getAllEmails_whenNoPersons_thenReturnEmptyList() {
        Set<String> result = emailService.getAllEmails("No city");

        assertTrue(result.isEmpty());
    }
}
