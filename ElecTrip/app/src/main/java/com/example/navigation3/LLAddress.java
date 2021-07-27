package com.example.navigation3;

public class LLAddress {
    private String latitude;
    private String longitude;
    private String address;

    public LLAddress(String latitude, String longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "LLAddress{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
