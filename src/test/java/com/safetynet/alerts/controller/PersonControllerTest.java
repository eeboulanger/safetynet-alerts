package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IPersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IPersonService service;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Person person = new Person(
            "Firstnme",
            "Lastname",
            "address",
            "city",
            123,
            "phone",
            "email"
    );

    @Test
    public void createTest() throws Exception {
        when(service.create(person)).thenReturn(true);

        mockMvc.perform(post("/person")
                        .content(mapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Person has been successfully saved"));

        verify(service, times(1)).create(person);
    }

    @Test
    public void createFailsTest() throws Exception {
        when(service.create(person)).thenReturn(false);

        mockMvc.perform(post("/person")
                        .content(mapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Saving person has failed"));

        verify(service, times(1)).create(person);
    }

    @Test
    public void updateTest() throws Exception {
        when(service.update(person)).thenReturn(true);

        mockMvc.perform(put("/person")
                        .content(mapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Person has been successfully updated"));

        verify(service, times(1)).update(person);
    }

    @Test
    public void updateFailsTest() throws Exception {
        when(service.update(person)).thenReturn(false);

        mockMvc.perform(put("/person")
                        .content(mapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Updating person has failed"));

        verify(service, times(1)).update(person);
    }

    @Test
    public void deleteTest() throws Exception {
        Map<String, String> map = Map.of(
                "firstName", person.getFirstName(),
                "lastName", person.getLastName()
                );
        when(service.delete(map)).thenReturn(true);

        mockMvc.perform(delete("/person")
                        .content(mapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Person has been successfully deleted"));

        verify(service, times(1)).delete(map);
    }

    @Test
    public void deleteFailsTest() throws Exception {
        Map<String, String> map = Map.of(
                "firstName", person.getFirstName(),
                "lastName", person.getLastName()
                );
        when(service.delete(map)).thenReturn(false);

        mockMvc.perform(delete("/person")
                        .content(mapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Deleting person has failed"));

        verify(service, times(1)).delete(map);
    }
}
