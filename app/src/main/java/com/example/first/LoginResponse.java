package com.example.first;


import java.io.Serializable;

public class LoginResponse implements Serializable {

    private Boolean error;
    private String message;
    private User user;

    public LoginResponse(Boolean error, String message, User user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
