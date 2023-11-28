package com.sheffield.model;

public class Address {
    private String userId; //foreign key
    private String houseNumber;
    private String roadName;
    private String cityName;
    private String postcode;

    // Constructor to initialize a User object with its attributes

    public Address(String houseNumber, String roadName, String cityName, String postcode, String userId) {
        this.setHouseNumber(houseNumber);
        this.setRoadName(roadName);
        this.setCityName(cityName);
        this.setPostcode(postcode);
        this.setUserId(userId);
    }

    // Getter and setter methods for the userId attribute with validation
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
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
            " userId='" + getUserId() + "'" +
            ", houseNumber='" + getHouseNumber() + "'" +
            ", roadName ='" + getRoadName() + "'" +
            ", cityName ='" + getCityName() + "'" +
            ", postcode ='" + getPostcode() + "'" +
            " }";
    }

}
