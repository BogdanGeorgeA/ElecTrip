package com.example.navigation3;

import java.util.Arrays;

public class Station {
    private String id;
    private String externalID;
    private Coordinates coordinates;
    private Evses[] evses;
    private Charger[] chargers;
    private Address physical_address;
    private Amenities amenities;
    private StationSpeedType speed;
   // User user;
    private Float price_per_kw;

    public Station(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getId() {
        return id;
    }

    public String getExternalID() {
        return externalID;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Evses[] getEvse() {
        return evses;
    }

    public Charger[] getChargers() {
        return chargers;
    }

    public Address getPhysical_address() {
        return physical_address;
    }

    public Amenities getAmenities() {
        return amenities;
    }

    public StationSpeedType getSpeed() {
        return speed;
    }

    public Float getPrice_per_kw() {
        return price_per_kw;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id='" + id + '\'' +
                ", externalID='" + externalID + '\'' +
                ", coordinates=" + coordinates +
                ", evse=" + Arrays.toString(evses) +
                ", chargers=" + Arrays.toString(chargers) +
                ", physical_address=" + physical_address +
                ", amenities=" + amenities +
                ", speed=" + speed +
                ", price_per_kw=" + price_per_kw +
                '}';
    }
}
