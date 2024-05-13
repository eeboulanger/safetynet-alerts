package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ChildDTO;
import com.safetynet.alerts.service.IChildAlertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ChildAlertController.class)
public class ChildAlertControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IChildAlertService<ChildDTO> childAlertService;

    @Test
    public void getListOfChildrenTest() throws Exception {
        mockMvc.perform(get("/childAlert")
                        .param("address", "Some address"))
                .andExpect(status().isOk());
    }
}
