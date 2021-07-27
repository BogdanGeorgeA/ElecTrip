package com.example.navigation3;


import java.util.Arrays;

class Evses {
    private String uid;
    private StatusEVSE status;
    private Connector[] connectors;

    public String getUid() {
        return uid;
    }

    public StatusEVSE getStatus() {
        return status;
    }

    public Connector[] getConnectors() {
        return connectors;
    }

    @Override
    public String toString() {
        return "Evse{" +
                "uid='" + uid + '\'' +
                ", status=" + status +
                ", connectors=" + Arrays.toString(connectors) +
                '}';
    }
}
