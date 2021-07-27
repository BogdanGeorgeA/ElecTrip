package com.example.navigation3;

class Geolocation {
    private String latitude;
    private String longitude;

    public Geolocation(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
