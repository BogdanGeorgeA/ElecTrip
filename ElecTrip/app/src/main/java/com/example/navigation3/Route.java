package com.example.navigation3;

import java.util.Arrays;

public class Route {
    private String status;
    private Integer charges;
    private Integer distance;
    private Integer duration;
    private Float consumption;
    private Float rangeStartKwh;
    private Float rangeEndKwh;
    private Leg[] legs;
    private String polyline;

    public String getStatus() {
        return status;
    }

    public Integer getCharges() {
        return charges;
    }

    public Integer getDistance() {
        return distance;
    }

    public Integer getDuration() {
        return duration;
    }

    public Float getConsumption() {
        return consumption;
    }

    public Float getRangeStartKwh() {
        return rangeStartKwh;
    }

    public Float getRangeEndKwh() {
        return rangeEndKwh;
    }

    public Leg[] getLegs() {
        return legs;
    }

    public String getPolyline() {
        return polyline;
    }

    @Override
    public String toString() {
        return "Route{" +
                "status='" + status + '\'' +
                ", charges=" + charges +
                ", distance=" + distance +
                ", duration=" + duration +
                ", consumption=" + consumption +
                ", rangeStartKwh=" + rangeStartKwh +
                ", rangeEndKwh=" + rangeEndKwh +
                ", legs=" + Arrays.toString(legs) +
                ", polyline='" + polyline + '\'' +
                '}';
    }
}
