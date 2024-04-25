package com.safetynet.alerts.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Component
public class AgeCalculator {
    public static int calculateAge(String birthdate, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate dateOfBirth = LocalDate.parse(birthdate, formatter);

        LocalDate today = LocalDate.now();

        return Period.between(dateOfBirth, today).getYears();
    }
}
