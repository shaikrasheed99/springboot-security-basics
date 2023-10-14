package com.springsecuritybasics.helpers.requests;

public record SignupRequest(String username, String password, String role, String firstname, String lastname) {
}