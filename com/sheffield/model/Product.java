package com.sheffield.model;

public class Product {
    private int productId; //foreign key
    private String productName;
    private String brandName;
    private float retailPrice;
    private String gaugeType;
    private String productCode;

    // Constructor to initialize a User object with its attributes

    public Product(int productId, String productName, String brandName, float retailPrice, String gaugeType, String productCode) {
        this.setProductId(productId);
        this.setProductName(productName);
        this.setBrandName(brandName);
        this.setRetailPrie(retailPrice);
        this.setGaugeType(gaugeType);
        this.setProductCode(productCode);
    }

    // Getter and setter methods for the userId attribute with validation
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setRetailPrie(float retailPrice) {
        this.retailPrice = retailPrice;
    }

    public float getRetailPrice() {
        return retailPrice;
    }

    public void setGaugeType(String gaugeType) {
        this.gaugeType = gaugeType;
    }

    public String getGaugeType() {
        return gaugeType;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode(String productCode) {
        return productCode;
    }



    @Override
    public String toString() {
        return "{ " +
            " productId='" + getProductId() + "'" +
            ", productName='" + getProductName() + "'" +
            ", brandName ='" + getBrandName() + "'" +
            ", retailPrice ='" + getRetailPrice() + "'" +
            ", gaugeType ='" + getGaugeType() + "'" +
            " }";
    }

}
