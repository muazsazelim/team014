package com.sheffield.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.sheffield.model.order.Order;
import com.sheffield.model.order.OrderLine;



public class OrderOperations {

    public Order[] getAllOrdersByUser (String userId, Connection connection) throws SQLException {
        try{
            String selectSQL = "SELECT * FROM Orders WHERE userId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> orderList = new ArrayList<>();
            
            while(resultSet.next()) {
               
                Order order = new Order(resultSet.getInt("orderID"),
                                        resultSet.getString("userId"), 
                                        resultSet.getDate("issueDate"),
                                        resultSet.getDouble("totalCost"), 
                                        resultSet.getString("status"));
                
                System.out.println("{" +
                        "OrderId='" + resultSet.getInt("OrderID") + "'" +
                        ", userID='" + resultSet.getString("UserID") + "'" +
                        ", DATE='" + resultSet.getDate("IssueDate") + "'" +
                        ", cost='" +resultSet.getDouble("TotalCost") + "'" +
                        ",status'" + resultSet.getString("Status") + "'" +
                        "}");
                orderList.add(order);

                
            } 

            return orderList.toArray(new Order[orderList.size()] );
        } catch (SQLException e){
            e.printStackTrace();
            throw e;
        }

        
    }

    public OrderLine[] getAllOrdersLinesByOrder (int orderID, Connection connection) throws SQLException {
        try{
            String selectSQL = "SELECT * FROM Order_Line WHERE orderID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, orderID);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<OrderLine> orderList = new ArrayList<>();
            while(resultSet.next()) {
               
                System.out.println("this is also working");
                OrderLine orderline = new OrderLine(resultSet.getInt("orderLineID"),
                                        resultSet.getInt("orderID"), 
                                        resultSet.getInt("productID"),
                                        resultSet.getInt("quantity"), 
                                        resultSet.getDouble("lineCost"));

                 System.out.println("{" +
                        "OrderLineId='" + resultSet.getInt("orderLineID") + "'" +
                        ", ProductID='" + resultSet.getInt("productID") + "'" +
                        ", Quantity='" + resultSet.getInt("quantity") + "'" +
                        ", LineCost='" +resultSet.getInt("lineCost") + "'" +                       
                        "}");
                orderList.add(orderline);

                
            }

            return orderList.toArray(new OrderLine[orderList.size()] );
        } catch (SQLException e){
            e.printStackTrace();
            throw e;
        }

        
    }

    public String getProductCode (int productID, Connection connection) throws SQLException {
        try{
            String selectSQL = "SELECT productCo FROM Product WHERE productID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, productID);
            ResultSet resultSet = preparedStatement.executeQuery();
           
            if(resultSet.next()) {               
               return resultSet.getString("productCo");               
            }else{
                return "Not Found";
            }
           
        } catch (SQLException e){
            e.printStackTrace();
            throw e;
        }        
    }

    public String getProductBrand (int productID, Connection connection) throws SQLException {
        try{
            String selectSQL = "SELECT brandName FROM Product WHERE productID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, productID);
            ResultSet resultSet = preparedStatement.executeQuery();
           
            if(resultSet.next()) {               
               return resultSet.getString("brandName");               
            }else{
                return "Not Found";
            }
           
        } catch (SQLException e){
            e.printStackTrace();
            throw e;
        }        
    }

    public String getProductName (int productID, Connection connection) throws SQLException {
        try{
            String selectSQL = "SELECT productName FROM Product WHERE productID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, productID);
            ResultSet resultSet = preparedStatement.executeQuery();
           
            if(resultSet.next()) {               
               return resultSet.getString("productName");               
            }else{
                return "Not Found";
            }
           
        } catch (SQLException e){
            e.printStackTrace();
            throw e;
        }        
    }
}
