package com.ibtikartechs.apps.el7a2.utilities;

public class AddressBook {
    int id, countryid, cityid, districtid;
    String countryname, cityname, districtname, latitude, longtude, name, mobile, address, delivary_availability, fees;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCountryid() {
        return countryid;
    }

    public void setCountryid(int countryid) {
        this.countryid = countryid;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public int getDistrictid() {
        return districtid;
    }

    public void setDistrictid(int districtid) {
        this.districtid = districtid;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDelivary_availability() {
        return delivary_availability;
    }

    public void setDelivary_availability(String delivary_availability) {
        this.delivary_availability = delivary_availability;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getDistrictname() {
        return districtname;
    }

    public void setDistrictname(String districtname) {
        this.districtname = districtname;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtude() {
        return longtude;
    }

    public void setLongtude(String longtude) {
        this.longtude = longtude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        String content = getName() + "\n" + getMobile() + "\n"
                + getCountryname() + " - " + getCityname() + " - " + getDistrictname();
        return content;
    }
}
