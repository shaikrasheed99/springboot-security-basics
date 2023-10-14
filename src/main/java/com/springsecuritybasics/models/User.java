package com.springsecuritybasics.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    public String username;
    public String password;
    public String role;
    public String firstname;
    public String lastname;

    public User username(String username) {
        this.username = username;
        return this;
    }

    public User password(String password) {
        this.password = password;
        return this;
    }

    public User role(String role) {
        this.role = role;
        return this;
    }

    public User firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public User lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }
}