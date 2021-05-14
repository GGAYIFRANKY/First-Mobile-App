package com.example.first.models;

public class UpdateResponse {

    private User user;
    private String message;
    private boolean error;

    public UpdateResponse(User user, String message, boolean error) {
        this.user = user;
        this.message = message;
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }
}
