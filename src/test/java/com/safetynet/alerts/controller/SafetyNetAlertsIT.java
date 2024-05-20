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
public class SafetyNetAlertsIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Given there's a firestation when entering number then return list of persons and count adults/children")
    public void givenFireStation_whenEnteringNumber_thenReturnList() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/firestation")
                        .param("stationNumber", "1")
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

        mockMvc.perform(MockMvcRequestBuilders.get("/firestation")
                        .param("stationNumber", "-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.personList.length()").value(0));
    }

    @Test
    @DisplayName("Given the address is covered, when searching by address, then return list of children")
    public void givenCoveredAddress_whenEnteringAddress_thenReturnListOfChildren() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
                        .param("address", "1509 Culver St")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.[*]firstName", hasItem("Tenley")))
                .andExpect(jsonPath("$.[0]familyMemberList.size()").value(4));
    }

    @Test
    @DisplayName("Given the address is not covered, when searching by address, then return empty list of children")
    public void givenNoCoveredAddress_whenEnteringAddress_thenReturnEmptyListOfChildren() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
                        .param("address", "Not covered")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("Given there are households covered, when searching by firestation number, then return list of unique phone numbers")
    public void givenCoveredHouseholds_whenEnteringStationNumber_thenReturnListOfPhoneNumbers() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                        .param("firestation", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$.[*]", hasItems("841-874-6512", "841-874-7462", "841-874-8547", "841-874-7784")));
    }

    @Test
    @DisplayName("Given there are no households covered, when searching by firestation number, then return empty list of unique phone numbers")
    public void givenNoCoveredHouseholds_whenEnteringStationNumber_thenReturnEmptyListOfPhoneNumbers() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                        .param("firestation", "-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("Given there are persons covered, when searching by address, then return list of persons and fire station number")
    public void givenCoveredHouseholds_whenEnteringAddress_thenReturnListOfPersonsAndFireStationNumber() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/fire")
                        .param("address", "951 LoneTree Rd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.firestation").value(2))
                .andExpect(jsonPath("$.persons.length()").value(1))
                .andExpect(jsonPath("$.persons[*].firstName", hasItem("Eric")))
                .andExpect(jsonPath("$.persons[*].lastName", hasItem("Cadigan")))
                .andExpect(jsonPath("$.persons[*].allergies.length()").value(0))
                .andExpect(jsonPath("$.persons[0].medications[*]", hasItem("tradoxidine:400mg")));
    }

    @Test
    @DisplayName("Given there are no persons covered, when searching by address, then return empty list")
    public void givenNoCoveredPersons_whenEnteringAddress_thenReturnEmptyList() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/fire")
                        .param("address", "address not covered")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.firestation").value(0))
                .andExpect(jsonPath("$.persons.length()").value(0));
    }

    @Test
    @DisplayName("Given there are persons covered, when searching by list of stations, then return station number address and list of persons with medical information")
    public void givenCoveredPersons_whenEnteringLisOfStations_thenReturnList() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations")
                        .param("stations", "1,2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()", is(6)))
                .andExpect(jsonPath("$[*].firestationNumber", hasItems(1, 2)))
                .andExpect(jsonPath("$[*].address", hasItems(
                        "908 73rd St",
                        "947 E. Rose Dr",
                        "644 Gershwin Cir",
                        "951 LoneTree Rd",
                        "892 Downing Ct",
                        "29 15th St"
                )))
                .andExpect(jsonPath("$[*].medicalInfoList.length()", containsInAnyOrder(2, 3, 1, 1, 3, 1))); //assert number of persons per address
    }

    @Test
    @DisplayName("Given there are no persons covered, when searching by station numbers, then return empty list")
    public void givenNoCoveredPersons_whenEnteringStationNumbers_thenReturnEmptyList() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations")
                        .param("stations", "-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("Given there are persons, when searching by city, then return list of emails")
    public void givenCoveredPersons_whenEnteringCity_thenReturnList() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
                        .param("city", "Culver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value(15));
    }

    @Test
    @DisplayName("Given there are no persons, when searching by city, then return empty list")
    public void givenNoCoveredPersons_whenEnteringCity_thenReturnEmptyList() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
                        .param("city", "No city")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("Given there are persons, when searching by name, then return  list")
    public void givenPerson_whenEnteringName_thenReturnList() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/personInfo")
                        .param("firstName", "John")
                        .param("lastName", "Boyd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].firstName", is("John")))
                .andExpect(jsonPath("$.[0].lastName", is("Boyd")))
                .andExpect(jsonPath("$.[0].email", is("jaboyd@email.com")))
                .andExpect(jsonPath("$.[0].medications.length()", is(2)))
                .andExpect(jsonPath("$.[0].allergies.length()", is(1)));
    }

    @Test
    @DisplayName("Given there are no persons, when searching by name, then return empty list")
    public void givenNoPerson_whenEnteringName_thenReturnEmptyList() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/personInfo")
                        .param("firstName", "No")
                        .param("lastName", "Name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
