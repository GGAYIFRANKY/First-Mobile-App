package com.example.first.models;

public class DeleteResponse {

    private String message;
    private boolean error;

    public DeleteResponse(String message, boolean error) {
        this.message = message;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }
}
