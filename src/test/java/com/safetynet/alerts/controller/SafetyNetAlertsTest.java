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
        when(childAlertService.findAllChildren("Some address")).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/childAlert")
                        .param("address", "Some address"))
                .andExpect(status().is2xxSuccessful());

        verify(childAlertService).findAllChildren("Some address");
    }

    @Test
    public void getListOfEmailsTest() throws Exception {
        when(emailService.getAllEmails("Some city")).thenReturn(new HashSet<>());

        mockMvc.perform(get("/communityEmail")
                        .param("city", "Some city"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getFireDtoTest() throws Exception {
        FireDTO fireDTO = new FireDTO(new ArrayList<>(), 1);
        when(fireAndFloodService.findPersonsAndFireStation("Some address")).thenReturn(fireDTO);

        mockMvc.perform(get("/fire")
                        .param("address", "Some address"))
                .andExpect(status().is2xxSuccessful());

        verify(fireAndFloodService).findPersonsAndFireStation("Some address");
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
        List<PersonInfoDTO> result = List.of(new PersonInfoDTO("Firstname", "Lastname", "email",
                19, new ArrayList<>(), new ArrayList<>()));
        when(personInfoService.getAllPersonsByName("Firstname", "Lastname")).thenReturn(result);

        mockMvc.perform(get("/personInfo")
                        .param("firstName", "Firstname")
                        .param("lastName", "Lastname"))
                .andExpect(status().is2xxSuccessful());

        verify(personInfoService).getAllPersonsByName("Firstname", "Lastname");
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
