package com.solvd.onlineshop.models;

public class User {
    private String firstName;
    private String lastName;
    private int age;
    private int addressId;

    public User(String firstName, String lastName, int age, int addressId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.addressId = addressId;
    }

    public User() {}

}
