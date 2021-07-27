package com.example.navigation3;

public class GetPaymentInfo {
    private String stationId;
    private Float kwH;

    public GetPaymentInfo(String stationId, Float kwH){
        this.stationId = stationId;
        this.kwH = kwH;
    }

    @Override
    public String toString() {
        return "GetPaymentInfo{" +
                "stationId='" + stationId + '\'' +
                ", kwH=" + kwH +
                '}';
    }
}
