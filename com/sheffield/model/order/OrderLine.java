package com.sheffield.model.order;

public class OrderLine {
    private int orderLineID;
    private int orderID;
    private int productID;
    private int quantity;
    private double lineCost;
    // needs validatiom to be done
    public OrderLine(int orderLineID, int orderID, int productID, int quantity, double lineCost){
        this.setOrderLineID(orderLineID);      
        this.setOrderID(orderID);
        this.setProductID(productID);
        this.setQuantity(quantity);
        this.setLineCost(lineCost);
    }

    public int getOrderLineID(){
        return orderLineID;
    }

    public int getOrderID(){
        return orderID;
    }

    public int getProductID(){
        return productID;
    }

    public int getQuantity(){
        return quantity;
    }

    public double getLineCost(){
        return lineCost;
    }

    public void setOrderLineID(int orderLineID){
        this.orderLineID = orderLineID;
    }

    public void setOrderID(int orderID){
        this.orderID = orderID;
    }

    public void setProductID(int productID){
        this.productID = productID;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void setLineCost(double lineCost){
        this.lineCost = lineCost;
    }
}
