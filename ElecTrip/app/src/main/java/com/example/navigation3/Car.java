package com.example.navigation3;

public class Car {

    private String manufacturerText;
    private String modelText;
    private String plateText;
    private String capacityText;
    private String yearOfFabricationText;
    private String averageConsumptionText;
    private String typeText;
    private String subTypeText;

    public Car() {
        this.averageConsumptionText = null;
        this.manufacturerText = null;
        this.modelText = null;
        this.plateText = null;
        this.capacityText = null;
        this.yearOfFabricationText = null;
        this.typeText = null;
        this.subTypeText = null;
    }

    public Car(String manufacturerText, String modelText, String plateText, String capacityText, String yearOfFabricationText, String averageConsumptionText, String typeText, String subTypeText) {
        this.manufacturerText = manufacturerText;
        this.modelText = modelText;
        this.plateText = plateText;
        this.capacityText = capacityText;
        this.yearOfFabricationText = yearOfFabricationText;
        this.averageConsumptionText = averageConsumptionText;
        this.typeText = typeText;
        this.subTypeText = subTypeText;
    }

    public String getYearOfFabricationText() {
        return yearOfFabricationText;
    }

    public String getAverageConsumptionText() {
        return averageConsumptionText;
    }

    public String getCapacityText() {
        return this.capacityText;
    }

    public String getManufacturerText() {
        return this.manufacturerText;
    }

    public String getModelText() {
        return this.modelText;
    }

    public String getPlateText() {
        return this.plateText;
    }

    public String getTypeText() {
        return this.typeText;
    }

    public String getSubTypeText() {
        return this.subTypeText;
    }

    public void setManufacturerText(String manufacturerText) {
        this.manufacturerText = manufacturerText;
    }

    public void setModelText(String modelText) {
        this.modelText = modelText;
    }

    public void setPlateText(String plateText) {
        this.plateText = plateText;
    }

    public void setCapacityText(String capacityText) {
        this.capacityText = capacityText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public void setSubTypeText(String subTypeText) {
        this.subTypeText = subTypeText;
    }

    public void setYearOfFabricationText(String yearOfFabricationText) {
        this.yearOfFabricationText = yearOfFabricationText;
    }

    public void setAverageConsumptionText(String averageConsumptionText) {
        this.averageConsumptionText = averageConsumptionText;
    }

    public void afisare() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Car{" +
                "manufacturerText='" + manufacturerText + '\'' +
                ", modelText='" + modelText + '\'' +
                ", plateText='" + plateText + '\'' +
                ", capacityText='" + capacityText + '\'' +
                ", yearOfFabricationText='" + yearOfFabricationText + '\'' +
                ", averageConsumptionText='" + averageConsumptionText + '\'' +
                ", typeText='" + typeText + '\'' +
                ", subTypeText='" + subTypeText + '\'' +
                '}';
    }
}
