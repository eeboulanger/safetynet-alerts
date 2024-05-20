package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.*;
import com.safetynet.alerts.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ChildAlertController.class, EmailController.class,
        FireController.class, FireStationCoverageController.class, FloodController.class,
        PersonInfoController.class, PhoneAlertController.class})
public class SafetyNetAlertsTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IChildAlertService<ChildDTO> childAlertService;
    @MockBean
    private IEmailService emailService;
    @MockBean
    private IFireAndFloodService fireAndFloodService;
    @MockBean
    private IFireStationCoverageService<FireStationCoverageDTO> fireStationCoverageService;
    @MockBean
    private ICommunityService personInfoService;
    @MockBean
    private IPhoneAlertService phoneAlertService;


    @Test
    public void getListOfChildrenTest() throws Exception {
        String address = "Rue des fleurs";
        when(childAlertService.findAllChildren(address)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/childAlert")
                        .param("address", address))
                .andExpect(status().is2xxSuccessful());

        verify(childAlertService).findAllChildren(address);
    }

    @Test
    public void getListOfEmailsTest() throws Exception {
        String city = "Denver";
        when(emailService.getAllEmails(city)).thenReturn(new HashSet<>());

        mockMvc.perform(get("/communityEmail")
                        .param("city", city))
                .andExpect(status().is2xxSuccessful());

        verify(emailService).getAllEmails(city);
    }

    @Test
    public void getFireDtoTest() throws Exception {
        String address = "Rue des fleurs";
        FireDTO fireDTO = new FireDTO(new ArrayList<>(), 1);
        when(fireAndFloodService.findPersonsAndFireStation(address)).thenReturn(fireDTO);

        mockMvc.perform(get("/fire")
                        .param("address", address))
                .andExpect(status().is2xxSuccessful());

        verify(fireAndFloodService).findPersonsAndFireStation(address);
    }

    @Test
    public void getFireStationCoverageTest() throws Exception {
        FireStationCoverageDTO result = new FireStationCoverageDTO(new ArrayList<>(), new HashMap<>());
        when(fireStationCoverageService.findPersonsCoveredByFireStation(1)).thenReturn(result);

        mockMvc.perform(get("/firestation", 1)
                        .param("stationNumber", "1"))
                .andExpect(status().is2xxSuccessful());

        verify(fireStationCoverageService).findPersonsCoveredByFireStation(1);
    }

    @Test
    public void getFloodDtoTest() throws Exception {
        FloodDTO result = new FloodDTO(1, "Some address", new ArrayList<>());
        List<FloodDTO> floodDTOList = List.of(result);
        when(fireAndFloodService.findAllHouseHoldsCoveredByStations(List.of(1))).thenReturn(floodDTOList);

        mockMvc.perform(get("/flood/stations")
                        .param("stations", "1"))
                .andExpect(status().is2xxSuccessful());

        verify(fireAndFloodService).findAllHouseHoldsCoveredByStations(List.of(1));
    }

    @Test
    public void getPersonInfoTest() throws Exception {
        String firstName = "Maya";
        String lastName = "Loyd";
        List<PersonInfoDTO> result = List.of(new PersonInfoDTO(firstName, lastName, "email",
                19, new ArrayList<>(), new ArrayList<>()));
        when(personInfoService.getAllPersonsByName(firstName, lastName)).thenReturn(result);

        mockMvc.perform(get("/personInfo")
                        .param("firstName", firstName)
                        .param("lastName", lastName))
                .andExpect(status().is2xxSuccessful());

        verify(personInfoService).getAllPersonsByName(firstName, lastName);
    }

    @Test
    public void getListOfPhoneNumbersTest() throws Exception {

        when(phoneAlertService.findPhoneNumbersByFireStation(1)).thenReturn(new HashSet<>());

        mockMvc.perform(get("/phoneAlert")
                        .param("firestation", "1"))
                .andExpect(status().is2xxSuccessful());

        verify(phoneAlertService).findPhoneNumbersByFireStation(1);
    }
}
