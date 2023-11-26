package com.sheffield.model;

public class Address {
    private int houseId;
    private String houseNumber;
    private String roadName;
    private String cityName;
    private String postcode;

    // Constructor to initialize a User object with its attributes

    public Address(int houseID, String houseNumber, String roadName, String cityName, String postcode) {
        this.setHouseId(houseID);
        this.setHouseNumber(houseNumber);
        this.setRoadName(roadName);
        this.setCityName(cityName);
        this.setPostcode(postcode);
    }

    // Getter and setter methods for the userId attribute with validation
    public void setHouseId(int houseID) {
        this.houseId = houseID;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPostcode() {
        return postcode;
    }



    @Override
    public String toString() {
        return "{ " +
            " houseId='" + getHouseId() + "'" +
            ", houseNumber='" + getHouseNumber() + "'" +
            ", roadName ='" + getRoadName() + "'" +
            ", cityName ='" + getCityName() + "'" +
            ", postcode ='" + getPostcode() + "'" +
            " }";
    }

}
