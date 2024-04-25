package com.safetynet.alerts.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PhoneAlertServiceIT {

    @Autowired
    private PhoneAlertService service;

    @Test
    @DisplayName("Given there are households covered, when search by firestation number then return list of phone numbers")
    public void findPhoneNumbersByFireStationCoverage_shouldReturnListOfPhoneNumbers() {

        Set<String> result = service.findPhoneNumbersByFireStation(1);

        assertEquals(4, result.size());
    }

    @Test
    @DisplayName("Given there are no households covered, when search by firestation number then return empty list")
    public void findPhoneNumbersWhenNoCoverage_shouldReturnEmptyList() {

        Set<String> result = service.findPhoneNumbersByFireStation(-1);

        assertEquals(0, result.size());
    }
}
