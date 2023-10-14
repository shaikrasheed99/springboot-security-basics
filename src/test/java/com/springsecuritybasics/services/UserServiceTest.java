package com.springsecuritybasics.services;

import com.springsecuritybasics.models.User;
import com.springsecuritybasics.repository.UserRepository;
import com.springsecuritybasics.helpers.requests.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private SignupRequest signupRequest;

    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequest(
                "testUsername",
                "testPassword",
                "testRole",
                "testFirstname",
                "testLastname"
        );
    }

    @Test
    void shouldBeAbleToSaveUserDetailsToRepository() {
        when(userRepository.save(any(User.class))).thenReturn(null);

        userService.signup(signupRequest);

        assertDoesNotThrow(() -> {});
    }
}