package com.sheffield.util;
import java.util.Date;
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

    public void insertOrder (Order order, Connection connection){
        try{
            String insertSQL = "INSERT INTO Orders (orderId, userId, issueDate, totalCost, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setInt(1, order.getOrderID());
            preparedStatement.setString(2, order.getUserID());
            preparedStatement.setDate(3, new java.sql.Date(order.getIssueDate().getTime()));
            preparedStatement.setDouble(4, order.getTotalCost());
            preparedStatement.setObject(5, order.getOrderStatus());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertOrderLine (OrderLine line, Connection connection){
        try{
        String insertSQL = "INSERT INTO Order_line (orderLineID, orderID, productID, quantity, lineCost) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

        preparedStatement.setInt(1, line.getOrderLineID());
        preparedStatement.setInt(2, line.getOrderID());
        preparedStatement.setInt(3, line.getProductID());
        preparedStatement.setInt(4, line.getQuantity());
        preparedStatement.setDouble(5, line.getLineCost());


        } catch (SQLException e){
            e.printStackTrace();
            
        }
    }
}
