package com.example.navigation3;

import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private List<Car2> listOfCars;
    private List<String> listOfChargingStations;
    private String paypalEmail;
    private String address;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPaypalEmail() {
        return paypalEmail;
    }

    public String getAddress() {
        return address;
    }

    public List<Car2> getListOfCars() {
        return listOfCars;
    }

    public List<String> getListOfChargingStations() {
        return listOfChargingStations;
    }
}

