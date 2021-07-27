package com.example.navigation3;

import com.google.gson.JsonObject;

public class ResponseNearbyStations {
    private boolean success;
    private String message;
    private JsonObject data;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public JsonObject getData() {
        return data;
    }
}
