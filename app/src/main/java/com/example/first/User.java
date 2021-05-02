package com.example.first;

public class User {
    private int id;
    private String email,username,phone_number;

    public User(int id, String email, String username, String phone_number) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.phone_number = phone_number;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone_number() {
        return phone_number;
    }
}
