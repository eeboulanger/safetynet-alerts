package com.safetynet.alerts.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmergencyControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Given there's a firestation when entering number then return list of personinfo and count adults/children")
    public void givenFireStation_whenEnteringNumber_thenReturnList() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/firestation?stationNumber={station_number}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.personList.length()").value(6))
                .andExpect(jsonPath("$.personList[*].firstName", hasItem("Reginold")))
                .andExpect(jsonPath("$.personList[*].lastName", hasItem("Walker")))
                .andExpect(jsonPath("$.personList[*].address", hasItem("908 73rd St")))
                .andExpect(jsonPath("$.personList[*].phoneNumber", hasItem("841-874-8547")))
                .andExpect(jsonPath("$.count.adults", is(5)))
                .andExpect(jsonPath("$.count.children", is(1)));
    }

    @Test
    @DisplayName("Given there's no firestation with the given number, then return empty Json")
    public void givenNoFireStation_whenEnteringNumber_thenReturnEmptyJson() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/firestation?stationNumber={station_number}", -1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.personList.length()").value(0));
    }

    @Test
    @DisplayName("Given the address is covered, when searching by address, then return list of children")
    public void givenCoveredAddress_whenEnteringAddress_thenReturnListOfChildren() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/childAlert?address={address}", "1509 Culver St")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.[*]firstName", hasItem("Tenley")))
                .andExpect(jsonPath("$.[0]familyMemberList.size()").value(4));
    }

    @Test
    @DisplayName("Given there are households covered, when searching by firestation number, then return list of unique phone numbers")
    public void givenCoveredHouseholds_whenEnteringStationNumber_thenReturnListOfPhoneNumbers() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert?firestation={station_number}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$.[*]", hasItems("841-874-6512", "841-874-7462", "841-874-8547", "841-874-7784")));
    }
}
