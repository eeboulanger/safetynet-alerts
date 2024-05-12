package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.DataPrepareService;
import com.safetynet.alerts.model.MedicalRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalControllerIT {
    private final ObjectMapper mapper = new ObjectMapper();
    private DataPrepareService dataPrepareService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        dataPrepareService = new DataPrepareService();
    }

    @AfterEach
    public void tearDown() throws IOException {
        dataPrepareService.resetData();
    }

    @Test
    public void createMedicalRecordTest() throws Exception {
        MedicalRecord record = new MedicalRecord(
                "New record", "", "", new ArrayList<>(), new ArrayList<>()
        );
        String requestBody = mapper.writeValueAsString(record);

        mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Medical record has been successfully created"));
    }

    @Test
    @DisplayName("Given there is already a medical record with the given name, then create should fail")
    public void createMedicalRecordFailsTest() throws Exception {
        MedicalRecord record = dataPrepareService.getMedicalRecord(0);
        String requestBody = mapper.writeValueAsString(record);

        mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Creating medical record has failed"));
    }

    @Test
    public void updateMedicalRecordTest() throws Exception {
        MedicalRecord record = dataPrepareService.getMedicalRecord(1);
        record.setBirthdate("new birthdate");
        String requestBody = mapper.writeValueAsString(record);

        mockMvc.perform(MockMvcRequestBuilders.put("/medicalRecord")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Medical record has been successfully updated"));
    }

    @Test
    @DisplayName("Given there is no medical record with the given name, then updating should fail")
    public void updateMedicalRecordFailsTest() throws Exception {
        MedicalRecord record = new MedicalRecord(
                "firstname",
                "lastname",
                "",
                new ArrayList<>(),
                new ArrayList<>()
        );
        String requestBody = mapper.writeValueAsString(record);

        mockMvc.perform(MockMvcRequestBuilders.put("/medicalRecord")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Updating medical record has failed"));
    }

    @Test
    public void deleteMedicalRecordTest() throws Exception {
        MedicalRecord record = dataPrepareService.getMedicalRecord(1);
        String requestBody = mapper.writeValueAsString(record);

        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecord")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Medical record has been successfully deleted"));
    }

    @Test
    @DisplayName("Given there is no medical record with the given name, then delete should fails")
    public void deleteMedicalRecordFailsTest() throws Exception {
        MedicalRecord record = new MedicalRecord(
                "firstname",
                "lastname",
                "",
                new ArrayList<>(),
                new ArrayList<>()
        );
        String requestBody = mapper.writeValueAsString(record);

        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecord")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Deleting medical record has failed"));
    }
}
