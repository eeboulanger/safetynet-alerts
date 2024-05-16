package com.safetynet.alerts.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

class FireStationCoverageDTOTest {

    @Test
    void testConstructorAndFieldAccess() {
        List<PersonContactInfo> personList = Arrays.asList(
                new PersonContactInfo("John", "Doe", "123 Main St", "123-456-7890"),
                new PersonContactInfo("Jane", "Smith", "456 Elm St", "987-654-3210")
        );
        Map<String, Integer> count = new HashMap<>();
        count.put("Adults", 5);
        count.put("Children", 3);

        FireStationCoverageDTO dto = new FireStationCoverageDTO(personList, count);

        assertIterableEquals(personList, dto.getPersonList());
        assertEquals(count, dto.getCount());
    }

    @Test
    void testEquality() {
        // Arrange
        List<PersonContactInfo> personList = List.of(
                new PersonContactInfo("John", "Doe", "123 Main St", "123-456-7890")
        );
        Map<String, Integer> count = new HashMap<>();
        count.put("Adults", 5);
        FireStationCoverageDTO dto1 = new FireStationCoverageDTO(personList, count);
        FireStationCoverageDTO dto2 = new FireStationCoverageDTO(personList, count);

        assertEquals(dto1, dto2, "Two instances with the same values should be equal.");
    }

    @Test
    void testHashCode() {
        // Arrange
        List<PersonContactInfo> personList = List.of(
                new PersonContactInfo("John", "Doe", "123 Main St", "123-456-7890")
        );
        Map<String, Integer> count = new HashMap<>();
        count.put("Adults", 5);
        FireStationCoverageDTO dto1 = new FireStationCoverageDTO(personList, count);
        FireStationCoverageDTO dto2 = new FireStationCoverageDTO(personList, count);

        assertEquals(dto1.hashCode(), dto2.hashCode(), "Hash codes should be equal for equal objects.");
    }
}
