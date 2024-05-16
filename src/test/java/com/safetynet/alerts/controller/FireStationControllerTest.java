package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.IFireStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IFireStationService service;
    private final ObjectMapper mapper = new ObjectMapper();
    private final FireStation station = new FireStation("address", 1);

    @Test
    public void createTest() throws Exception {
        when(service.create(station)).thenReturn(true);

        mockMvc.perform(post("/firestation")
                        .content(mapper.writeValueAsString(station))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Firestation has been successfully created"));

        verify(service, times(1)).create(station);
    }

    @Test
    public void createFailsTest() throws Exception {
        when(service.create(station)).thenReturn(false);

        mockMvc.perform(post("/firestation")
                        .content(mapper.writeValueAsString(station))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Creating firestation failed"));

        verify(service, times(1)).create(station);
    }

    @Test
    public void updateTest() throws Exception {
        when(service.update(station)).thenReturn(true);

        mockMvc.perform(put("/firestation")
                        .content(mapper.writeValueAsString(station))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Firestation has been successfully updated"));

        verify(service, times(1)).update(station);
    }

    @Test
    public void updateFailsTest() throws Exception {
        when(service.update(station)).thenReturn(false);

        mockMvc.perform(put("/firestation")
                        .content(mapper.writeValueAsString(station))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Updating firestation failed"));

        verify(service, times(1)).update(station);
    }

    @Test
    public void deleteTest() throws Exception {
        Map<String, String> map = Map.of(
                "address", station.getAddress(),
                "station", String.valueOf(station.getStation()
                ));
        when(service.delete(map)).thenReturn(true);

        mockMvc.perform(delete("/firestation")
                        .content(mapper.writeValueAsString(station))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Firestation has been successfully deleted"));

        verify(service, times(1)).delete(map);
    }

    @Test
    public void deleteFailsTest() throws Exception {
        Map<String, String> map = Map.of(
                "address", station.getAddress(),
                "station", String.valueOf(station.getStation()
                ));
        when(service.delete(map)).thenReturn(false);

        mockMvc.perform(delete("/firestation")
                        .content(mapper.writeValueAsString(station))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Deleting firestation failed"));

        verify(service, times(1)).delete(map);
    }
}
