package com.springsecuritybasics.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldBeAbleToReturnSuccessAuthenticatedMessage() throws Exception {
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/authenticated")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(
                MockMvcResultMatchers
                        .status()
                        .isOk()
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.status").value("success")
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.code").value("OK")
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.data.message").value("This API is accessed by only authenticated users")
        );
    }
}