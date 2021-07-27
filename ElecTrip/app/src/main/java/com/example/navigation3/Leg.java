package com.example.navigation3;

public class Leg {
    private String stationId;
    private Float chargeTime;
    private Float chargeKW;

    public String getStationId() {
        return stationId;
    }

    public Float getChargeTime() {
        return chargeTime;
    }

    public Float getChargeKW() {
        return chargeKW;
    }

    @Override
    public String toString() {
        return "Leg{" +
                "stationId='" + stationId + '\'' +
                ", chargeTime=" + chargeTime +
                ", chargeKW=" + chargeKW +
                '}';
    }
}
