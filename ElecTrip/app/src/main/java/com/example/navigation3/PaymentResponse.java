package com.example.navigation3;

import java.io.Serializable;

public class PaymentResponse implements Serializable {
    private Boolean success;
    private String message;
    private String payment;

    public PaymentResponse(Boolean success, String message, String payment) {
        this.success = success;
        this.message = message;
        this.payment = payment;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "PaymentResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", payment='" + payment + '\'' +
                '}';
    }
}
