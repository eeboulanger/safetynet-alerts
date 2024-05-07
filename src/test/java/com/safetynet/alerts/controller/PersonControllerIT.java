package com.safetynet.alerts.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Disabled
    @DisplayName("Given there is person with the name, then return successfully deleted message")
    public void deletePersonTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/person")
                        .param("firstName", "No such person")
                        .param("lastName", "Test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Person has been successfully deleted"));
    }

    @Test
    @DisplayName("Given there is no person with the name, then return failed to delete message")
    public void deletePerson_whenNoPerson_shouldFailTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/person")
                        .param("firstName", "Test")
                        .param("lastName", "Test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Failed to delete person with the given name"));
    }
}
