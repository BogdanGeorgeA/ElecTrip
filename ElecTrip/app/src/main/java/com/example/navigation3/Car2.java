package com.example.navigation3;

import androidx.annotation.NonNull;

public class Car2 {
    private String id;
    private String make;
    private String carModel;
    private String edition;
    private Integer power;
    private Float acceleration;
    private Integer topSpeed;
    private Integer torque;
    private Integer seats;
    private Integer weight;
    private Integer width;
    private ImageData imagesData;

    public String getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getEdition() {
        return edition;
    }

    public Integer getPower() {
        return power;
    }

    public Float getAcceleration() {
        return acceleration;
    }

    public Integer getTopSpeed() {
        return topSpeed;
    }

    public Integer getTorque() {
        return torque;
    }

    public Integer getSeats() {
        return seats;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getWidth() {
        return width;
    }

    public ImageData getImagesData() {
        return imagesData;
    }

    public String getInfo() {
        return "Power: " + power + "\n" + "Acceleration: " + acceleration + "\n" + "Top Speed: " + topSpeed + " km/h" + "\n"
                + "Torque: " + torque + "\n" + "Seats: " + seats + "\n" + "Weight: " + weight + "\n" + "Width: " + width;
    }

    @NonNull
    @Override
    public String toString() {
        return carModel + " " + edition;
    }
}