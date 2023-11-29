package com.sheffield.model.order;
import java.util.Date;

public class Order {
    private int orderID;
    private String userID;
    private Date issueDate;
    private double totalCost;
    private OrderStatus status;

    public Order(int orderID, String userID, Date issueDate, double totalCost, String status ){
        this.setOrderID(orderID);
        this.setIssueDate(issueDate);
        this.setOrderStatus(status);
        this.setTotalCost(totalCost);
        this.setUserId(userID);
    }

    public int getOrderID(){
        return orderID;
    }

    public String getUserID(){
        return userID;
    }

    public Date getIssueDate(){
        return issueDate;
    }

    public double getTotalCost(){
        return totalCost;
    }

    public OrderStatus getOrderStatus(){
        return status;
    }

    public void setUserId(String userID) {
        this.userID = userID;
    }

    public void setOrderID(int orderID){
        this.orderID = orderID;
    }

    public void setIssueDate(Date issueDate) {
        if (issueDate != null && !issueDate.after(new Date())) {
            this.issueDate = issueDate;
        } else {
            throw new IllegalArgumentException("future dates are not");
        }
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public void setOrderStatus(String status) {
        try {
            this.status = OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Not a valid status");
        }
    }


    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        FULFILLED
    }

    
}
