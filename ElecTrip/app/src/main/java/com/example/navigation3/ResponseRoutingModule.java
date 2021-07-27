package com.example.navigation3;

public class ResponseRoutingModule {
    private boolean success;
    private String message;
    private Route route;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Route getRoute() {
        return route;
    }
}
