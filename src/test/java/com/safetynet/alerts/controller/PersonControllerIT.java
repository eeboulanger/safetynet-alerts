package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.config.DataInitializer;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.*;
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
    private static Person person;
    private Person unexistingPerson;
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DataInitializer dataInitializer;

    @BeforeEach
    public void setUp() {

        dataInitializer.run();
        unexistingPerson = new Person(
                "No such person",
                "test",
                "1509 Culver St",
                "Culver",
                97451,
                "000-111-6512",
                "empty@email.com"
        );
        person = new Person(
                "John",
                "Boyd",
                "1509 Culver St",
                "Culver",
                97451,
                "841-874-6512",
                "jaboyd@email.com"
        );
    }

    @Test
    @DisplayName("Given there's no person with the same name, then create new person should succeed")
    public void createPersonTest() throws Exception {
        Person createPerson = new Person();
        createPerson.setFirstName("New person");
        createPerson.setLastName("Test");

        String requestBody = mapper.writeValueAsString(createPerson);

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Person has been successfully saved"));
    }

    @Test
    @DisplayName("Given there's already a person with the same name, then create new person should fail")
    public void createPerson_whenPersonAlreadyExists_shouldFail() throws Exception {
        //person exists already
        String requestBody = mapper.writeValueAsString(person);

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Saving person has failed"));
    }

    @Test
    @DisplayName("Given there's no person with the name, then update should fail")
    public void updatePerson_whenNoPersonWithName_shouldFail() throws Exception {
        String requestBody = "{}";
        mockMvc.perform(MockMvcRequestBuilders.put("/person")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Updating person has failed"));
    }

    @Test
    public void updatePersonTest() throws Exception {
        person.setAddress("New address");
        String requestBody = mapper.writeValueAsString(person);

        mockMvc.perform(MockMvcRequestBuilders.put("/person")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Person has been successfully updated"));
    }

    @Test
    @DisplayName("Given there is person with the name, then return successfully deleted message")
    public void deletePersonTest() throws Exception {
        String requestBody = mapper.writeValueAsString(person);
        mockMvc.perform(MockMvcRequestBuilders.delete("/person")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Person has been successfully deleted"));
    }

    @Test
    @DisplayName("Given there is no person with the name, then return failed to delete message")
    public void deletePerson_whenNoPerson_shouldFailTest() throws Exception {
        String requestBody = mapper.writeValueAsString(unexistingPerson);
        mockMvc.perform(MockMvcRequestBuilders.delete("/person")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value("Deleting person has failed"));
    }
}
