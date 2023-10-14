package com.springsecuritybasics.services;

import com.springsecuritybasics.models.User;
import com.springsecuritybasics.repository.UserRepository;
import com.springsecuritybasics.helpers.requests.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public void signup(SignupRequest signupRequest) {
        User user = new User()
                .username(signupRequest.username())
                .password(passwordEncoder.encode(signupRequest.password()))
                .role(signupRequest.role())
                .firstname(signupRequest.firstname())
                .lastname(signupRequest.lastname());

        userRepository.save(user);
    }
}