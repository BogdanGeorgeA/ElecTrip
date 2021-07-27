package com.example.navigation3;


import java.util.Arrays;

public class RoutingModule {
    private String carId;
    private Integer max_kilometers;
    private float kilowats_now;
    private Plug plugs[];
    private Plug adapters[];
    private Integer number_of_passengers;


    private static RoutingModule routingModuleInstance = null;

    public static RoutingModule getInstance() {
        if (routingModuleInstance == null)
            routingModuleInstance = new RoutingModule();

        return routingModuleInstance;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public Integer getMax_kilometers() {
        return max_kilometers;
    }

    public void setMax_kilometers(Integer max_kilometers) {
        this.max_kilometers = max_kilometers;
    }

    public float getKilowats_now() {
        return kilowats_now;
    }

    public void setKilowats_now(float kilowats_now) {
        this.kilowats_now = kilowats_now;
    }

    public Plug[] getPlugs() {
        return plugs;
    }

    public void setPlugs(Plug[] plugs) {
        this.plugs = plugs;
    }

    public Plug[] getAdapters() {
        return adapters;
    }

    public void setAdapters(Plug[] adapters) {
        this.adapters = adapters;
    }

    public Integer getNumber_of_passengers() {
        return number_of_passengers;
    }

    public void setNumber_of_passengers(Integer number_of_passengers) {
        this.number_of_passengers = number_of_passengers;
    }

    @Override
    public String toString() {
        return "RoutingModule{" +
                "max_kilometers=" + max_kilometers +
                ", kilowats_now=" + kilowats_now +
                ", plugs=" + Arrays.toString(plugs) +
                ", adapters=" + Arrays.toString(adapters) +
                ", number_of_passengers=" + number_of_passengers +
                '}';
    }
}
