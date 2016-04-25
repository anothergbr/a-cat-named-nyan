package com.gbr.nyan.appdirect.entity;

public class EventUser {
    private String uuid;
    private String openId;
    private String email;
    private String firstName;
    private String lastName;

    public String getUuid() {
        return uuid;
    }

    public String getOpenId() {
        return openId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
