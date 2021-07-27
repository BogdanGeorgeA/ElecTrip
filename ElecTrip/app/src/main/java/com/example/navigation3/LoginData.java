package com.example.navigation3;

public class LoginData {
    private String email;
    private String password;

    public LoginData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
