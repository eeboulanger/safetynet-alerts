package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.IMedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IMedicalRecordService service;
    private final ObjectMapper mapper = new ObjectMapper();
    private final MedicalRecord record = new MedicalRecord(
            "Firstnme",
            "Lastname",
            "01/01/1950",
            new ArrayList<>(),
            new ArrayList<>()
    );

    @Test
    public void createTest() throws Exception {
        when(service.create(record)).thenReturn(true);

        mockMvc.perform(post("/medicalRecord")
                        .content(mapper.writeValueAsString(record))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Medical record has been successfully created"));

        verify(service, times(1)).create(record);
    }

    @Test
    public void createFailsTest() throws Exception {
        when(service.create(record)).thenReturn(false);

        mockMvc.perform(post("/medicalRecord")
                        .content(mapper.writeValueAsString(record))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Creating medical record has failed"));

        verify(service, times(1)).create(record);
    }

    @Test
    public void updateTest() throws Exception {
        when(service.update(record)).thenReturn(true);

        mockMvc.perform(put("/medicalRecord")
                        .content(mapper.writeValueAsString(record))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Medical record has been successfully updated"));

        verify(service, times(1)).update(record);
    }

    @Test
    public void updateFailsTest() throws Exception {
        when(service.update(record)).thenReturn(false);

        mockMvc.perform(put("/medicalRecord")
                        .content(mapper.writeValueAsString(record))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Updating medical record has failed"));

        verify(service, times(1)).update(record);
    }

    @Test
    public void deleteTest() throws Exception {
        Map<String, String> map = Map.of(
                "firstname", record.getFirstName(),
                "lastname", String.valueOf(record.getLastName()
                ));
        when(service.delete(map)).thenReturn(true);

        mockMvc.perform(delete("/medicalRecord")
                        .content(mapper.writeValueAsString(record))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Medical record has been successfully deleted"));

        verify(service, times(1)).delete(map);
    }

    @Test
    public void deleteFailsTest() throws Exception {
        Map<String, String> map = Map.of(
                "firstname", record.getFirstName(),
                "lastname", String.valueOf(record.getLastName()
                ));
        when(service.delete(map)).thenReturn(false);

        mockMvc.perform(delete("/medicalRecord")
                        .content(mapper.writeValueAsString(record))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Deleting medical record has failed"));

        verify(service, times(1)).delete(map);
    }
}
