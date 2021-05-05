package com.example.first.models;

import java.io.Serializable;

public class User implements Serializable{
    private int id;
    private String username;
    private String email;
    private String phone_number;

    public User(int id, String username, String email,  String phone_number) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone_number = phone_number;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }
}
