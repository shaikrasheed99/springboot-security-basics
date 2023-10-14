package com.springsecuritybasics.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

public class SuccessResponse {
    public HttpStatus code;
    public String status;
    public Object data;

    public SuccessResponse code(HttpStatus code) {
        this.code = code;
        return this;
    }

    public SuccessResponse status(String status) {
        this.status = status;
        return this;
    }

    public SuccessResponse data(Object data) {
        this.data = data;
        return this;
    }

    public String convertToJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}