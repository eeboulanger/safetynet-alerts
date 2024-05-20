package com.safetynet.alerts.service;

import com.safetynet.alerts.config.DataInitializer;
import com.safetynet.alerts.dto.FireStationCoverageDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FireStationCoverageDTOServiceIT {

    @Autowired
    private FireStationCoverageService service;
    @Autowired
    private DataInitializer dataInitializer;

    @BeforeEach
    public void setUp() {
        dataInitializer.run();
    }

    @Nested
    class ListTests {
        List<Person> personList;
        List<MedicalRecord> recordList;

        @BeforeEach
        public void setUp() {
            personList = service.findAllPersons(1);
        }

        @Test
        public void findAllPersonsCoveredByFireStationTest() {
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
    public void findAllPersonsCoveredByFireStationTest() {
        FireStationCoverageDTO coverage = service.findPersonsCoveredByFireStation(1);

        assertNotNull(coverage);
        assertEquals(6, coverage.getPersonList().size());
        assertEquals(5, coverage.getCount().get("adults"));
        assertEquals(1, coverage.getCount().get("children"));
    }
}
