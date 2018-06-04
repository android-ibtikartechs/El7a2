package com.ibtikartechs.apps.el7a2.data.models;

public class AddressModel {
    private String addressId;
    private String name;
    private String countryName;
    private String governmentName;
    private String cityName;
    private String latitude;
    private String longtude;
    private  String mobNum;
    private  String address;
    private boolean delivaryAvailability;
    private boolean isSelected;
    private String fees;

    public AddressModel(String addressId, String countryName, String governmentName, String cityName, String latitude, String longtude, String name, String mobNum, String address, boolean delivaryAvailability, String fees) {
        this.addressId = addressId;
        this.countryName = countryName;
        this.governmentName = governmentName;
        this.cityName = cityName;
        this.latitude = latitude;
        this.longtude = longtude;
        this.name = name;
        this.mobNum = mobNum;
        this.address = address;
        this.delivaryAvailability = delivaryAvailability;
        this.fees = fees;
    }

    public AddressModel(String addressId, String name, String governmentName, String cityName, String latitude, String longtude, String address, boolean delivaryAvailability) {
        this.addressId = addressId;
        this.name = name;
        this.governmentName = governmentName;
        this.cityName = cityName;
        this.latitude = latitude;
        this.longtude = longtude;
        this.address = address;
        this.delivaryAvailability = delivaryAvailability;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getAddressId() {
        return addressId;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getGovernmentName() {
        return governmentName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongtude() {
        return longtude;
    }

    public String getName() {
        return name;
    }

    public String getMobNum() {
        return mobNum;
    }

    public String getAddress() {
        return address;
    }

    public boolean isDelivaryAvailabl() {
        return delivaryAvailability;
    }

    public String getFees() {
        return fees;
    }
}
