package com.example.navigation3;

public class ChargingPort {
    private String address;
    private String city;
    private String price;
    private String format;
    private String powerType;
    private String standard;

    public ChargingPort(String address, String city, String price, String format, String powerType, String standard) {
        this.address = address;
        this.city = city;
        this.price = price;
        this.format = format;
        this.powerType = powerType;
        this.standard = standard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    @Override
    public String toString() {
        return "ChargingPort{" +
                "address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", price='" + price + '\'' +
                ", format='" + format + '\'' +
                ", powerType='" + powerType + '\'' +
                ", standard='" + standard + '\'' +
                '}';
    }
}
