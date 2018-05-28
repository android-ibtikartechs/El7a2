package com.ibtikartechs.apps.el7a2.data.models;

public class UserModel {
    private String dbId;
    private String userId;
    private String name;
    private String email;
    private String mobNum;
    private String address;
    private String gov;
    private String city;

    public UserModel(String dbId, String userId, String name, String email, String mobNum, String address, String gov, String city) {
        this.dbId = dbId;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.mobNum = mobNum;
        this.address = address;
        this.gov = gov;
        this.city = city;
    }

    public String getDbId() {
        return dbId;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobNum() {
        return mobNum;
    }

    public String getAddress() {
        return address;
    }

    public String getGov() {
        return gov;
    }

    public String getCity() {
        return city;
    }
}
