package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStationCoverage;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmergencyServiceIT {

    @Autowired
    EmergencyService service;

    @Nested
    class ListTests {
        List<Person> personList;
        List<MedicalRecord> recordList;

        @BeforeEach
        public void setUp() {
            personList = service.findAllPersonsCoveredByStation(1);
        }

        @Test
        public void getPersonsCoveredByFireStationTest() {
            assertEquals(6, personList.size());
        }

        @Test
        public void getAllRecordsForPersonsInListTest() {
            recordList = service.getRecordsForAllPersonsInList(personList);
            assertEquals(6, recordList.size());
        }

        @Test
        public void getNumberOfChildrenAndAdultsTest() {
            recordList = service.getRecordsForAllPersonsInList(personList);
            Map<String, Integer> count = service.countAdultsAndChildren(recordList);

            assertEquals(1, count.get("children"));
            assertEquals(5, count.get("adults"));
        }
    }

    @Test
    public void isAdultTest() {
        String adult = "12/04/1984";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String child = LocalDate.now().format(formatter);

        assertTrue(service.isAdult(adult));
        assertFalse(service.isAdult(child));
    }

    @Test
    public void findAllPersonsCoveredByFireStationTest() {
        FireStationCoverage coverage = service.findPersonsCoveredByFireStation(1);

        assertNotNull(coverage);
        assertEquals(6, coverage.getPersonList().size());
        assertEquals(5, coverage.getCount().get("adults"));
        assertEquals(1, coverage.getCount().get("children"));
    }
}
