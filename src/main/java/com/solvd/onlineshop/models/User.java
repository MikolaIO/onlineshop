package com.solvd.onlineshop.models;

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private int addressId;

    public User(Long id, String firstName, String lastName, int age, int addressId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.addressId = addressId;
    }

    public User() {}

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public int getAddressId() {
        return addressId;
    }
}
