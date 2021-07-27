package com.example.navigation3;

import java.util.Arrays;

public class BodyRoute {
    private String carID;
    private Integer max_kilometers;
    private Float kilowats_now;
    private Plug[] plugs;
    private Plug[] adapters;
    private Integer number_of_passengers;
    private LLAddress origin;
    private LLAddress destination;

    public Float getKilowats_now() {
        return kilowats_now;
    }

    public BodyRoute(RoutingModule routingModule){
        this.carID = routingModule.getCarId();
        this.max_kilometers = routingModule.getMax_kilometers();
        this.kilowats_now = routingModule.getKilowats_now();
        this.plugs = routingModule.getPlugs();
        this.adapters = routingModule.getAdapters();
        this.number_of_passengers = routingModule.getNumber_of_passengers();
    }

    public BodyRoute(String carID, Integer max_kilometers, Float kilowats_now, Plug[] plugs, Plug[] adapters, Integer number_of_passengers, LLAddress origin, LLAddress destination) {
        this.carID = carID;
        this.max_kilometers = max_kilometers;
        this.kilowats_now = kilowats_now;
        this.plugs = plugs;
        this.adapters = adapters;
        this.number_of_passengers = number_of_passengers;
        this.origin = origin;
        this.destination = destination;
    }

    public LLAddress getOrigin() {
        return origin;
    }

    public void setOrigin(LLAddress origin) {
        this.origin = origin;
    }

    public LLAddress getDestination() {
        return destination;
    }

    public void setDestination(LLAddress destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "BodyRoute{" +
                "carID='" + carID + '\'' +
                ", max_kilometers=" + max_kilometers +
                ", kilowats_now=" + kilowats_now +
                ", plugs=" + Arrays.toString(plugs) +
                ", adapters=" + Arrays.toString(adapters) +
                ", number_of_passengers=" + number_of_passengers +
                ", origin=" + origin +
                ", destination=" + destination +
                '}';
    }
}
