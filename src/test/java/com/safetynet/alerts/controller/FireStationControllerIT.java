package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.config.DataInitializer;
import com.safetynet.alerts.model.FireStation;
import org.junit.jupiter.api.BeforeEach;
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
public class FireStationControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DataInitializer dataInitializer;
    private final ObjectMapper mapper = new ObjectMapper();
    private FireStation station;

    @BeforeEach
    public void setUp() {
        dataInitializer.run();
        station = new FireStation("1509 Culver St", 3);
    }


    @Test
    public void createFireStationTest() throws Exception {
        FireStation station = new FireStation("Address", 123);
        String requestBody = mapper.writeValueAsString(station);

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Firestation has been successfully created"));
    }

    @Test
    @DisplayName("Given the fire station already exists, then creating new fire station should fail ")
    public void createFireStationFailsTest() throws Exception {
        String requestBody = mapper.writeValueAsString(station);

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Creating firestation failed"));
    }

    @Test
    public void updateFireStationTest() throws Exception {
        String requestBody = mapper.writeValueAsString(station);

        mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Firestation has been successfully updated"));
    }

    @Test
    @DisplayName("Given there is no fire station with the given address, updating fire station should fail")
    public void updateFireStationFailsTest() throws Exception {
        FireStation station = new FireStation("No such address", 1);
        String requestBody = mapper.writeValueAsString(station);

        mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Updating firestation failed"));
    }

    @Test
    public void deleteFireStationTest() throws Exception {
        String requestBody = mapper.writeValueAsString(station);

        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Firestation has been successfully deleted"));

    }

    @Test
    @DisplayName("Given there is no fire station with the given address, delete should fail")
    public void deleteFireStationFailsTest() throws Exception {
        FireStation station = new FireStation("No such address", 1);
        String requestBody = mapper.writeValueAsString(station);
        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Deleting firestation failed"));
    }
}
