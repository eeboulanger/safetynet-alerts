package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildDTO;
import com.safetynet.alerts.dto.PersonContactInfo;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.AgeCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChildDTOAlertServiceTest {
    @Mock
    private IPersonService personService;
    @Mock
    private IMedicalRecordService recordService;
    @InjectMocks
    private ChildAlertService service;
    private Person child;
    private MedicalRecord record;
    private List<Person> listOfFamilyMembers;

    @BeforeEach
    public void setUp() {
        String address = "1 rue de test";
        child = new Person("Emilia",
                "Boyd", address,
                "City", 123, "000", "email@");
        record = new MedicalRecord("Emilia",
                "Boyd",
                "01/01/2020", null, null);

        listOfFamilyMembers = List.of(new Person(
                        "John",
                        "Boyd", address,
                        "City", 123, "000", "email@"
                ), new Person(
                        "Theresa",
                        "Boyd", address,
                        "City", 123, "000", "email@"
                )
        );
    }

    @Test
    @DisplayName("Given there is one child, then person repository should be called two times and return a list of 1 child")
    public void findAllChildrenTest() {
        Person father = listOfFamilyMembers.get(0);
        MedicalRecord medicalRecordFather = new MedicalRecord(
                father.getFirstName(),
                father.getLastName(),
                "01/01/1950",
                null, null
        );
        Person mother = listOfFamilyMembers.get(1);
        MedicalRecord medicalRecordMother = new MedicalRecord(
                mother.getFirstName(),
                mother.getLastName(),
                "01/01/1955",
                null, null
        );
        String address = child.getAddress();
        List<Person> persons = List.of(child, father, mother);

        when(personService.findByAddress(address)).thenReturn(Optional.of(persons));
        when(recordService.findByName(child.getFirstName(), child.getLastName())).thenReturn(Optional.of(record));
        when(recordService.findByName(father.getFirstName(), father.getLastName()))
                .thenReturn(Optional.of(medicalRecordFather));
        when(recordService.findByName(mother.getFirstName(), mother.getLastName()))
                .thenReturn(Optional.of(medicalRecordMother));

        List<ChildDTO> result = service.findAllChildren(address);

        List<String> expectedFamilyMembers = listOfFamilyMembers.stream().map(Person::getFirstName).toList();
        List<String> resultFamilyMemberList = result.get(0).getFamilyMemberList().stream().map(PersonContactInfo::getFirstName).toList();

        verify(personService, times(2)).findByAddress(address);
        verify(recordService).findByName(child.getFirstName(), child.getLastName());
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(child.getFirstName(), result.get(0).getFirstName());
        assertEquals(child.getLastName(), result.get(0).getLastName());
        assertEquals(AgeCalculator.calculateAge(record.getBirthdate(), "MM/dd/yyyy"), result.get(0).getAge());
        assertEquals(expectedFamilyMembers, resultFamilyMemberList);
    }

    @Test
    @DisplayName("given there is no person with the address, then person repository should be called one time and return an empty list")
    public void findAllChildren_whenNoPersonAtAddressTest() {
        String address = "No person at the address";

        when(personService.findByAddress(address)).thenReturn(Optional.empty());

        List<ChildDTO> result = service.findAllChildren(address);

        verify(personService, times(1)).findByAddress(address);
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Given there is no Child at the address, then person repository should be called one time and return an empty list")
    public void findAllChildren_whenNoChildTest() {
        Person adultOnly = listOfFamilyMembers.get(0);
        MedicalRecord medicalRecord = new MedicalRecord(
                adultOnly.getFirstName(),
                adultOnly.getLastName(),
                "01/01/1950",
                null, null
        );
        String address = adultOnly.getAddress();
        when(personService.findByAddress(address)).thenReturn(Optional.of(listOfFamilyMembers));
        when(recordService.findByName(anyString(), anyString())).thenReturn(Optional.of(medicalRecord));

        List<ChildDTO> result = service.findAllChildren(address);

        verify(personService, times(1)).findByAddress(address);
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Given there are family members return a list of persons")
    public void findFamilyMembersTest_shouldCallPersonRepository() {
        String address = child.getAddress();
        when(personService.findByAddress(address)).thenReturn(Optional.of(listOfFamilyMembers));

        List<PersonContactInfo> result = service.findFamilyMembers(child.getLastName(), address, child.getFirstName());

        List<String> expectedFamilyMembers = listOfFamilyMembers.stream().map(Person::getFirstName).toList();
        List<String> resultFamilyMemberList = result.stream().map(PersonContactInfo::getFirstName).toList();

        verify(personService, times(1)).findByAddress(address);
        assertEquals(expectedFamilyMembers, resultFamilyMemberList);
    }
}
