package com.example.db_project;

public class CurrentUser {
    private static int userID;
    private static String username;
    private static String firstName;
    private static String lastName;
    private static String email;
    private static String password;
    private static int phone;

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        CurrentUser.userID = userID;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        CurrentUser.username = username;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String fullName) {
        CurrentUser.firstName = fullName;
    }

    public static void clear() {
        userID = 0;
        username = null;
        firstName = null;
    }
}

