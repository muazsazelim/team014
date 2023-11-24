package com.sheffield.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.sheffield.model.DatabaseConnectionHandler;
import com.sheffield.model.order.Order;
import com.sheffield.model.order.OrderLine;
//import com.sheffield.model.DatabaseOperations;
import com.sheffield.model.user.User;


public class OrderOperations {

    public Order[] getAllOrdersByUser (String userId, Connection connection) throws SQLException {
        try{
            String selectSQL = "SELECT * FROM Orders WHERE userId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> orderList = new ArrayList<>();
            if (resultSet.next()) {
               
                Order order = new Order(resultSet.getInt("orderID"),
                                        resultSet.getString("userID"), 
                                        resultSet.getDate("issueDate"),
                                        resultSet.getDouble("totalCost"), 
                                        resultSet.getString("status"));
                
                System.out.println("{" +
                        "OrderId='" + resultSet.getInt("orderID") + "'" +
                        ", userID='" + resultSet.getString("userID") + "'" +
                        ", DATE='" + resultSet.getDate("issueDate") + "'" +
                        ", cost='" +resultSet.getDouble("totalCost") + "'" +
                        ",status'" + resultSet.getString("status") + "'" +
                        "}");
                orderList.add(order);

                
            } else {
                System.out.println("");
            }

            return orderList.toArray(new Order[0] );
        } catch (SQLException e){
            e.printStackTrace();
            throw e;
        }

        
    }

    public OrderLine[] getAllOrdersLinesByOrder (int orderID, Connection connection) throws SQLException {
        try{
            String selectSQL = "SELECT * FROM Order_Lines WHERE orderID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, orderID);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<OrderLine> orderList = new ArrayList<>();
            if (resultSet.next()) {
               
                OrderLine order = new OrderLine(resultSet.getInt("orderLineID"),
                                        resultSet.getInt("orderID"), 
                                        resultSet.getInt("productID"),
                                        resultSet.getInt("quantity"), 
                                        resultSet.getDouble("lineCost"));
                orderList.add(order);

                
            } else {
                System.out.println("");
            }

            return orderList.toArray(new OrderLine[0] );
        } catch (SQLException e){
            e.printStackTrace();
            throw e;
        }

        
    }
}
