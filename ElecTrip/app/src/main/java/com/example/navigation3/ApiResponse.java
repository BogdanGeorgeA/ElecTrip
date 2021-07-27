package com.example.navigation3;

import java.util.List;

public class ApiResponse {
    private boolean success;
    private String message;
    private ApiData data;
    private User user;
    private List<Payment> payments;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Car2> getCarList() {
        return data.getCarList();
    }

    public User getUser() {
        return user;
    }

    public ApiData getData() {
        return data;
    }

    public List<Payment> getPayments() {
        return payments;
    }
}
