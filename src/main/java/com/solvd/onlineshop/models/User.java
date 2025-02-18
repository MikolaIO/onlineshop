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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
}
