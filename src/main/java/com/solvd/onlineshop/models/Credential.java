package com.solvd.onlineshop.models;

public class Credential {
    private Long id;
    private String name;
    private String password;
    private String mail;
    private Long userId;

    public Credential(String name, String password, String mail) {
        this.name = name;
        this.password = password;
        this.mail = mail;
    }

    public Credential() {}

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public Long getUserId() {
        return userId;
    }
}
