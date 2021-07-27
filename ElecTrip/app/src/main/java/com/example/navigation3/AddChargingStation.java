package com.example.navigation3;

import java.util.Arrays;

public class AddChargingStation {
    String address;
    String city;
    Float price;
    StatusEVSE status;
    Connector[] connectors;
    Coordinates geolocation;
    String password;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public StatusEVSE getStatus() {
        return status;
    }

    public void setStatus(StatusEVSE status) {
        this.status = status;
    }

    public Connector[] getConnectors() {
        return connectors;
    }

    public void setConnectors(Connector[] connectors) {
        this.connectors = connectors;
    }

    public Coordinates getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Coordinates geolocation) {
        this.geolocation = geolocation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AddChargingStation(String address, String city, Float price, StatusEVSE status, Connector[] connectors, Coordinates geolocation, String password) {
        this.address = address;
        this.city = city;
        this.price = price;
        this.status = status;
        this.connectors = connectors;
        this.geolocation = geolocation;
        this.password = password;
    }

    @Override
    public String toString() {
        return "AddChargingStation{" +
                "address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", connectors=" + Arrays.toString(connectors) +
                ", geolocation=" + geolocation +
                ", password='" + password + '\'' +
                '}';
    }
}
