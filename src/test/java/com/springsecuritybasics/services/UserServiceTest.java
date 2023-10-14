package com.springsecuritybasics.services;

import com.springsecuritybasics.helpers.responses.UserResponse;
import com.springsecuritybasics.models.User;
import com.springsecuritybasics.repository.UserRepository;
import com.springsecuritybasics.helpers.requests.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;


    private SignupRequest signupRequest;
    private User user;

    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequest(
                "testUsername",
                "testPassword",
                "testRole",
                "testFirstname",
                "testLastname"
        );

        user = new User()
                .username(signupRequest.username())
                .password(signupRequest.password())
                .firstname(signupRequest.firstname())
                .lastname(signupRequest.lastname())
                .role(signupRequest.role());
    }

    @Test
    void shouldBeAbleToSaveUserDetailsToRepository() {
        when(userRepository.save(any(User.class))).thenReturn(null);
        when(passwordEncoder.encode(any(String.class))).thenReturn("testHashPassword");

        userService.signup(signupRequest);

        assertDoesNotThrow(() -> {
        });

        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(any(String.class));
    }

    @Test
    void shouldBeAbleToReturnAllUsers() {
        var userList = new ArrayList<User>();
        userList.add(user);

        when(userRepository.findAll()).thenReturn(userList);

        List<UserResponse> users = userService.getUsers();

        assertEquals(users.get(0).username(), userList.get(0).username);

        verify(userRepository, times(1)).findAll();
    }
}