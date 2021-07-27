package com.example.navigation3;

import java.util.List;

public class ListOfUserCars {
    public static List<Car2> getUserCars() {
        return userCars;
    }

    public static void setUserCars(List<Car2> userCars) {
        ListOfUserCars.userCars = userCars;
    }

    private static List<Car2> userCars;

}
