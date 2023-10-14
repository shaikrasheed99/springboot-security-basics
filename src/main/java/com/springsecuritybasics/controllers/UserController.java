package com.springsecuritybasics.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springsecuritybasics.helpers.responses.SuccessResponse;
import com.springsecuritybasics.helpers.requests.SignupRequest;
import com.springsecuritybasics.helpers.responses.UserResponse;
import com.springsecuritybasics.models.User;
import com.springsecuritybasics.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping("/authenticated")
    public ResponseEntity<?> authenticated() throws JsonProcessingException {
        String responseMessage = "This API is accessed by only authenticated users";

        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("message", responseMessage);

        String response = new SuccessResponse()
                .code(HttpStatus.OK)
                .status("success")
                .data(dataMap)
                .convertToJson();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) throws JsonProcessingException {
        userService.signup(signupRequest);

        String responseMessage = "user successfully signed up";
        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("message", responseMessage);

        String response = new SuccessResponse()
                .code(HttpStatus.CREATED)
                .status("success")
                .data(dataMap)
                .convertToJson();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() throws JsonProcessingException {
        List<UserResponse> users = userService.getUsers();

        String response = new SuccessResponse()
                .code(HttpStatus.OK)
                .status("success")
                .data(users)
                .convertToJson();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username, Principal principal) throws JsonProcessingException {
        if (!username.equals(principal.getName())) {
            String response = new SuccessResponse()
                    .code(HttpStatus.FORBIDDEN)
                    .status("error")
                    .data("Cannot access another user details")
                    .convertToJson();

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        User user = userService.getUserByUsername(username);

        UserResponse userResponse = new UserResponse(
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getRole()
        );

        String response = new SuccessResponse()
                .code(HttpStatus.OK)
                .status("success")
                .data(userResponse)
                .convertToJson();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}