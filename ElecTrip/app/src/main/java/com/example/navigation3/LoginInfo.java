package com.example.navigation3;

public class LoginInfo {
    private String email;
    private String password;

    public LoginInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}