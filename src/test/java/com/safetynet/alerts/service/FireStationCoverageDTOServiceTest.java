package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonContactInfo;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.dto.FireStationCoverageDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FireStationCoverageDTOServiceTest {

    @Mock
    IFireStationService fireStationService;
    @Mock
    IPersonService personService;
    @Mock
    IMedicalRecordService recordService;

    @InjectMocks
    FireStationCoverageService service;

    @Test
    public void getListOfPersonsCoveredByStationTest() {
        List<FireStation> list = List.of(new FireStation(
                "address", 1)
        );
        List<Person> personList = List.of(new Person(
                "Person1",
                "Lastname",
                "address",
                "city",
                123,
                "phone",
                "email"
        ));
        MedicalRecord record = new MedicalRecord("Person1", "Lastname", "01/01/1950", null, null);

        when(fireStationService.findByStationNumber(1)).thenReturn(Optional.of(list));
        when(personService.findByAddress("address")).thenReturn(Optional.of(personList));
        when(recordService.findByName("Person1", "Lastname")).thenReturn(Optional.of(record));

        FireStationCoverageDTO coverage = service.findPersonsCoveredByFireStation(1);

        verify(fireStationService).findByStationNumber(1);
        verify(personService).findByAddress("address");
        verify(recordService).findByName("Person1", "Lastname");
        assertEquals(1, coverage.getPersonList().size());
        assertEquals(1, coverage.getCount().get("adults"));
    }

    @Test
    public void getListOfPersonsCoveredByStationFailsTest() {
        when(fireStationService.findByStationNumber(1)).thenReturn(Optional.empty());

        FireStationCoverageDTO coverage = service.findPersonsCoveredByFireStation(1);

        verify(fireStationService).findByStationNumber(1);
        verify(personService, never()).findByAddress(anyString());
        verify(recordService, never()).findByName(anyString(), anyString());
        assertEquals(0, coverage.getPersonList().size());
    }

    @Test
    public void countAdultsAndChildrenTest() {
        List<MedicalRecord> list = List.of(
                new MedicalRecord("Adult", "lastname", "01/01/1950", new ArrayList<>(), new ArrayList<>()),
                new MedicalRecord("Child", "lastname", "01/01/2020", new ArrayList<>(), new ArrayList<>())
        );
        Map<String, Integer> result = service.countAdultsAndChildren(list);

        assertEquals(1, result.get("adults"));
        assertEquals(1, result.get("children"));
    }

    @Test
    public void findAllPersonsTest() {
        List<FireStation> list = List.of(new FireStation(
                "address", 1)
        );
        List<Person> personList = List.of(new Person(
                "Person1",
                "Lastname",
                "address",
                "city",
                123,
                "phone",
                "email"
        ));

        when(fireStationService.findByStationNumber(1)).thenReturn(Optional.of(list));
        when(personService.findByAddress("address")).thenReturn(Optional.of(personList));

        List<Person> result = service.findAllPersons(1);

        verify(fireStationService).findByStationNumber(1);
        verify(personService).findByAddress("address");
        assertEquals(1, result.size());
    }

    @Test
    public void getPersonInfoListTest() {
        List<Person> personList = List.of(new Person(
                "Person1",
                "Lastname",
                "address",
                "city",
                123,
                "phone",
                "email"
        ));
        List<PersonContactInfo> result = service.getPersonInfoList(personList);

        assertEquals(1, result.size());
        assertEquals("Person1", result.get(0).getFirstName());
    }

    @Test
    public void getRecordsForAllPersonsInListTest() {
        List<Person> personList = List.of(new Person(
                "Person1",
                "Lastname",
                "address",
                "city",
                123,
                "phone",
                "email"
        ));
        MedicalRecord record = new MedicalRecord("Person1", "Lastname", "01/01/1950", null, null);
        when(recordService.findByName("Person1", "Lastname")).thenReturn(Optional.of(record));

        List<MedicalRecord> result = service.getRecordsForAllPersonsInList(personList);

        verify(recordService).findByName("Person1", "Lastname");
        assertEquals(1, result.size());
        assertEquals("Person1", result.get(0).getFirstName());
    }

    @Test
    public void getMedicalRecordTest() {
        MedicalRecord record = new MedicalRecord("Person1", "Lastname", "01/01/1950", null, null);
        when(recordService.findByName("Person1", "Lastname")).thenReturn(Optional.of(record));

        Optional<MedicalRecord> result = service.getMedicalRecord("Person1", "Lastname");

        verify(recordService).findByName("Person1", "Lastname");
        assertTrue(result.isPresent());
        assertEquals("Person1", result.get().getFirstName());
    }
}
