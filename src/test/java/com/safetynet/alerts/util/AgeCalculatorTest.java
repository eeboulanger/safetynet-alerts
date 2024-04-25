package com.safetynet.alerts.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.safetynet.alerts.util.AgeCalculator.calculateAge;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AgeCalculatorTest {

    @Test
    public void calculateAgeTest() {
        String adult = "12/04/1984";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String child = LocalDate.now().format(formatter);

        assertTrue(calculateAge(adult, "MM/dd/yyyy") > 18);
        assertTrue(calculateAge(child, "MM/dd/yyyy") <= 18);
    }
}
