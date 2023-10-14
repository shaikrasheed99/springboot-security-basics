package com.springsecuritybasics.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springsecuritybasics.helpers.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class UserController {
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
}