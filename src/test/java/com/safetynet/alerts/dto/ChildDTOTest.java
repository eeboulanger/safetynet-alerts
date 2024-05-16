package com.safetynet.alerts.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class ChildDTOTest {

    @Test
    void getChildDtoTest() {
        List<PersonContactInfo> family = Arrays.asList(
                new PersonContactInfo("John", "Doe", "1234 Road", "123-456-7890"),
                new PersonContactInfo("Jane", "Doe", "1234 Road", "987-654-3210")
        );
        String firstName = "Alice";
        String lastName = "Smith";
        int age = 10;

        ChildDTO childDTO = new ChildDTO(firstName, lastName, age, family);

        assertEquals(firstName, childDTO.getFirstName());
        assertEquals(lastName, childDTO.getLastName());
        assertEquals(age, childDTO.getAge());
        assertIterableEquals(family, childDTO.getFamilyMemberList());
    }
    @Test
    void testEquality() {
        List<PersonContactInfo> family = List.of(
                new PersonContactInfo("John", "Doe", "1234 Road", "123-456-7890")
        );
        ChildDTO childDTO1 = new ChildDTO("Alice", "Smith", 10, family);
        ChildDTO childDTO2 = new ChildDTO("Alice", "Smith", 10, family);

        assertEquals(childDTO1, childDTO2);
    }

    @Test
    void testHashCode() {
        List<PersonContactInfo> family = List.of(
                new PersonContactInfo("John", "Doe", "1234 Road", "123-456-7890")
        );
        ChildDTO childDTO1 = new ChildDTO("Alice", "Smith", 10, family);
        ChildDTO childDTO2 = new ChildDTO("Alice", "Smith", 10, family);

        assertEquals(childDTO1.hashCode(), childDTO2.hashCode(), "Hash codes should be equal for equal objects.");
    }
}
