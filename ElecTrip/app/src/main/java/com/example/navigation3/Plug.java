package com.example.navigation3;

public class Plug {
    private Standard standard;
    private Integer chargingPower;

    public Plug(Plug plug) {
        this.standard = plug.getStandard();
        this.chargingPower = plug.getChargingPower();
    }

    public Plug(Standard standard, Integer chargingPower) {
        this.standard = standard;
        this.chargingPower = chargingPower;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public Integer getChargingPower() {
        return chargingPower;
    }

    public void setChargingPower(Integer chargingPower) {
        this.chargingPower = chargingPower;
    }

    @Override
    public String toString() {
        return "Plug{" +
                "standard=" + standard +
                ", chargingPower=" + chargingPower +
                '}';
    }
}
