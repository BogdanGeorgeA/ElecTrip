package com.example.navigation3;

public class RegisterInfo {

    private String lastName;

    private String firstName;

    private String phoneNumber;

    private String email;

    private String password;

    public RegisterInfo(String lastName, String firstName, String phoneNumber, String email, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
