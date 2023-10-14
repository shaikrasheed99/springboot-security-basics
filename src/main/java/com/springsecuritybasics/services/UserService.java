package com.springsecuritybasics.services;

import com.springsecuritybasics.helpers.responses.UserResponse;
import com.springsecuritybasics.models.User;
import com.springsecuritybasics.repository.UserRepository;
import com.springsecuritybasics.helpers.requests.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public List<UserResponse> getUsers() {
        Iterable<User> users = userRepository.findAll();

        List<UserResponse> usersResponse = StreamSupport.stream(users.spliterator(), false)
                .map(user -> {
                    return new UserResponse(
                            user.getUsername(),
                            user.getFirstname(),
                            user.getLastname(),
                            user.getRole());
                })
                .collect(Collectors.toList());

        return usersResponse;
    }

    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findById(username);

        return user.get();
    }
}