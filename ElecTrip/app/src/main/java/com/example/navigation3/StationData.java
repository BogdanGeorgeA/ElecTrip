package com.example.navigation3;

public class StationData {
    private static StationData stationData=null;

    public AddChargingStation getAddChargingStation() {
        return addChargingStation;
    }

    private AddChargingStation addChargingStation=null;
    public static StationData getInstance(){
        if(stationData==null){
            stationData=new StationData();

        }
        return stationData;
    }


    public void setAddChargingStation(AddChargingStation addChargingStation) {
        this.addChargingStation = addChargingStation;
    }

}
