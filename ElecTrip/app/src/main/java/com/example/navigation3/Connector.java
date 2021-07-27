package com.example.navigation3;

public class Connector {
    private String id;
    private Standard standard;
    private Format format;
    private PowerType power_type;
    private Integer max_amperage;
    private Integer max_voltage;
    private Integer max_electric_power;
    private Integer power;

    public Connector() {
    }

    public Connector(Standard standard, Format format, PowerType power_type, Integer max_amperage, Integer max_voltage, Integer max_electric_power, Integer power) {

        this.standard = standard;
        this.format = format;
        this.power_type = power_type;
        this.max_amperage = max_amperage;
        this.max_voltage = max_voltage;
        this.max_electric_power = max_electric_power;
        this.power = power;
    }

    public String getId() {
        return id;
    }

    public Standard getStandard() {
        return standard;
    }

    public Format getFormat() {
        return format;
    }

    public PowerType getPower_type() {
        return power_type;
    }

    public Integer getMax_amperage() {
        return max_amperage;
    }

    public Integer getMax_voltage() {
        return max_voltage;
    }

    public Integer getMax_electric_power() {
        return max_electric_power;
    }

    public Integer getPower() {
        return power;
    }

    @Override
    public String toString() {
        return "Connector{" +
                "id='" + id + '\'' +
                ", standard=" + standard +
                ", format=" + format +
                ", power_type=" + power_type +
                ", max_amperage=" + max_amperage +
                ", max_voltage=" + max_voltage +
                ", max_electric_power=" + max_electric_power +
                ", power=" + power +
                '}';
    }
}
