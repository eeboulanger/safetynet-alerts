package com.safetynet.alerts.controller;

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
    @DisplayName("Given there's no person with the same name, then create new person should succeed")
    public void createPersonTest() throws Exception {

        String requestBody = "{\"firstName\":\"Test\",\"lastName\":\"Test\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Person has been successfully saved"));
    }
    @Test
    @DisplayName("Given there's already a person with the same name, then create new person should fail")
    public void createPerson_whenPersonAlreadyExists_shouldFail() throws Exception {

        String requestBody = "{\"firstName\":\"Eric\",\"lastName\":\"Cadigan\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Saving person has failed"));
    }

    @Test
    @DisplayName("Given there is person with the name, then return successfully deleted message")
    public void deletePersonTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/person")
                        .param("firstName", "Test")
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
                        .param("firstName", "No such name")
                        .param("lastName", "Test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Failed to delete person with the given name"));
    }
}
