package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildDTO;
import com.safetynet.alerts.dto.PersonContactInfo;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChildDTOAlertServiceTest {

    @Mock
    private PersonService personService;
    @Mock
    private MedicalRecordService recordService;
    @InjectMocks
    private ChildAlertService service;

    @Test
    @DisplayName("given there is one child, then person repository should be called two times and return a list of 1 child")
    public void findAllChildrenTest() {
        String address = "1 rue de test";
        Person person = new Person("John", "Boyd", address, "City", 123, "000", "email@");
        MedicalRecord record = new MedicalRecord("John", "Boyd", "01/01/2020", null, null);

        when(personService.findByAddress(address)).thenReturn(Optional.of(List.of(person)));
        when(recordService.findByName("John", "Boyd")).thenReturn(Optional.of(record));

        List<ChildDTO> result = service.findAllChildren(address);

        verify(personService, times(2)).findByAddress(address);
        verify(recordService).findByName("John", "Boyd");
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    @Test
    @DisplayName("given there is no person with the address, then person repository should be called one time and return an empty list")
    public void findAllChildren_whenNoPersonAtAddressTest() {
        String address = "1 rue de test";

        when(personService.findByAddress(address)).thenReturn(Optional.empty());

        List<ChildDTO> result = service.findAllChildren(address);

        verify(personService, times(1)).findByAddress(address);
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Given there is no Child at the address, then person repository should be called one time and return an empty list")
    public void findAllChildren_whenNoChildTest() {
        String address = "1 rue de test";
        Person person = new Person("John", "Boyd", address, "City", 123, "000", "email@");
        MedicalRecord record = new MedicalRecord("John", "Boyd", "01/01/1975", null, null);

        when(personService.findByAddress(address)).thenReturn(Optional.of(List.of(person)));
        when(recordService.findByName("John", "Boyd")).thenReturn(Optional.of(record));


        List<ChildDTO> result = service.findAllChildren(address);

        verify(personService, times(1)).findByAddress(address);
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Given there are family members return a list of persons")
    public void findFamilyMembersTest_shouldCallPersonRepository() {
        String lastName = "Boyd";
        String address = "1 rue de test";
        String childName = "John";
        Person mother = new Person("Marie", "Boyd", address, "City", 123, "000", "email@");
        Person father = new Person("Peter", "Boyd", address, "City", 123, "000", "email@");

        when(personService.findByAddress(address)).thenReturn(Optional.of(Arrays.asList(mother, father)));

        List<PersonContactInfo> result = service.findFamilyMembers(lastName, address, childName);

        verify(personService, times(1)).findByAddress(address);
        assertEquals(2, result.size());
    }


}
