package com.springsecuritybasics.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springsecuritybasics.helpers.requests.SignupRequest;
import com.springsecuritybasics.models.User;
import com.springsecuritybasics.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private SignupRequest signupRequest;

    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequest(
                "testUsername",
                "testPassword",
                "user",
                "testFirstname",
                "testLastname"
        );

        User user = new User()
                .username(signupRequest.username())
                .password(signupRequest.password())
                .role(signupRequest.role())
                .firstname(signupRequest.firstname())
                .lastname(signupRequest.lastname());

        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteById(signupRequest.username());
    }

    @Test
    void shouldBeAbleToReturnSuccessAuthenticatedMessage() throws Exception {
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/authenticated")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors
                                .user(signupRequest.username())
                                .password(signupRequest.password())
                        )
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

    @Test
    void shouldNotBeAbleToReturnSuccessAuthenticatedMessageForUnauthorisedUser() throws Exception {
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/authenticated")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(
                MockMvcResultMatchers
                        .status()
                        .isUnauthorized()
        );
    }

    @Test
    void shouldBeAbleToSignupUserSuccessfully() throws Exception {
        String signupRequestJson = new ObjectMapper().writeValueAsString(signupRequest);

        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signupRequestJson)
        );

        result.andExpect(
                MockMvcResultMatchers
                        .status()
                        .isCreated()
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.code").value("CREATED")
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.status").value("success")
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.data.message").value("user successfully signed up")
        );
    }

    @Test
    void shouldBeAbleToReturnAllUsersForUserWithAdminRole() throws Exception {
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(
                                SecurityMockMvcRequestPostProcessors
                                        .user(signupRequest.username())
                                        .password(signupRequest.password())
                                        .roles("admin")
                        )
        );

        result.andExpect(
                MockMvcResultMatchers
                        .status()
                        .isOk()
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.code").value("OK")
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.status").value("success")
        );
    }

    @Test
    void shouldNotBeAbleToReturnAllUsersForUserWithUserRole() throws Exception {
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(
                                SecurityMockMvcRequestPostProcessors
                                        .user(signupRequest.username())
                                        .password(signupRequest.password())
                                        .roles("user")
                        )
        );

        result.andExpect(
                MockMvcResultMatchers
                        .status()
                        .isForbidden()
        );
    }

    @Test
    void shouldBeAbleToReturnUserByUsername() throws Exception {
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/users/{username}", signupRequest.username())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(
                                SecurityMockMvcRequestPostProcessors
                                        .user(signupRequest.username())
                                        .password(signupRequest.password())
                        )
        );

        result.andExpect(
                MockMvcResultMatchers
                        .status()
                        .isOk()
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.code").value("OK")
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.status").value("success")
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.data.username").value(signupRequest.username())
        );
    }

    @Test
    void shouldNotBeAbleToReturnUserByUsernameForUnauthorisedUsers() throws Exception {
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/users/{username}", signupRequest.username())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(
                MockMvcResultMatchers
                        .status()
                        .isUnauthorized()
        );
    }

    @Test
    void shouldNotBeAbleToReturnUserByAnotherUsername() throws Exception {
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/users/{username}", "testUsername2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(
                                SecurityMockMvcRequestPostProcessors
                                        .user(signupRequest.username())
                                        .password(signupRequest.password())
                        )
        );

        result.andExpect(
                MockMvcResultMatchers
                        .status()
                        .isForbidden()
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.code").value("FORBIDDEN")
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.status").value("error")
        );
    }
}