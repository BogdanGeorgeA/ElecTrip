package com.example.navigation3;

public class Payment {
    private String id;
    private String status;
    private String provider;
    private String providerEmail;
    private String consumer;
    private String consumerEmail;
    private float pricePerKwCharged;
    private float totalPrice;
    private float kwCharged;
    private String createdDate;
    private String lastStatusUpdate;

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getProvider() {
        return provider;
    }

    public String getProviderEmail() {
        return providerEmail;
    }

    public String getConsumer() {
        return consumer;
    }

    public String getConsumerEmail() {
        return consumerEmail;
    }

    public float getPricePerKwCharged() {
        return pricePerKwCharged;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public float getKwCharged() {
        return kwCharged;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getLastStatusUpdate() {
        return lastStatusUpdate;
    }
}
