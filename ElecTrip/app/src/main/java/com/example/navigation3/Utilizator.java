package com.example.navigation3;


public class Utilizator {
    private String email;
    private String password;
    private String message;
    private Boolean success;
    private String AuthentificationKey;
    private String RefreshKey;

    public String getRefreshKey() {
        return RefreshKey;
    }

    public void setRefreshKey(String refreshKey) {
        RefreshKey = refreshKey;
    }

    private static Utilizator utilizator;

    public static Utilizator getUtilizatorInstance() {
        if (utilizator == null) {
            utilizator = new Utilizator("nume", "parola");
        }
        return utilizator;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getAuthentificationKey() {
        return AuthentificationKey;
    }

    public void setAuthentificationKey(String authentificationKey) {
        AuthentificationKey = authentificationKey;
    }

    public Utilizator(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }


    public Utilizator(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

