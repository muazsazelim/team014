package com.sheffield.model.order;

public class OrderLine {
    private int orderLineID;
    private int orderID;
    private int productID;
    private int quantity;
    private double lineCost;
    // needs validatiom to be done
    public OrderLine(int orderLineID, int orderID, int productID, int quantity, double lineCost){
        this.setLineCost(lineCost);
        this.setOrderID(orderID);
        this.setOrderLineID(orderLineID);
        this.setProductID(productID);
        this.setQuantity(quantity);

    }

    private int getOrderLineID(){
        return orderLineID;
    }

    private int getOrderID(){
        return orderID;
    }

    private int getProductID(){
        return productID;
    }

    private int getQuantity(){
        return quantity;
    }

    private double getLineCost(){
        return lineCost;
    }

    private void setOrderLineID(int orderLineID){
        this.orderLineID = orderLineID;
    }

    private void setOrderID(int orderID){
        this.orderID = orderID;
    }

    private void setProductID(int productID){
        this.productID = productID;
    }

    private void setQuantity(int quantity){
        this.quantity = quantity;
    }

    private void setLineCost(double lineCost){
        this.lineCost = lineCost;
    }
}
