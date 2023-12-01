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
import com.sheffield.model.user.User;

public class OrderOperations {

    public Order[] getAllOrders(Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Orders";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> orderList = new ArrayList<>();
            System.out.println("<=================== GET ALL Orders ====================>");
            while (resultSet.next()) {
                // Print each book's information in the specified format

                Order order = new Order(resultSet.getInt("orderID"),
                        resultSet.getString("userId"),
                        resultSet.getDate("issueDate"),
                        resultSet.getDouble("totalCost"),
                        resultSet.getString("status"));
                System.out.println("{" +
                        "OrderId='" + resultSet.getInt("OrderID") + "'" +
                        ", userID='" + resultSet.getString("UserID") + "'" +
                        ", DATE='" + resultSet.getDate("IssueDate") + "'" +
                        ", cost='" + resultSet.getDouble("TotalCost") + "'" +
                        ",status'" + resultSet.getString("Status") + "'" +
                        "}");
                orderList.add(order);

            }

            System.out.println("<======================================================>");
            return orderList.toArray(new Order[orderList.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public void addOrder(Order order, Connection connection) throws SQLException {
        try {
            // Create an SQL INSERT statement
            String insertSQL = "INSERT INTO Orders (orderId, userId, issueDate, totalCost, status) VALUES (?, ?, ?, ?, ?)";

            // Prepare and execute the INSERT statement
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, order.getOrderID());
            preparedStatement.setString(2, order.getUserID());
            preparedStatement.setDate(3, (java.sql.Date) order.getIssueDate());
            preparedStatement.setDouble(4, order.getTotalCost());
            preparedStatement.setString(5, order.getOrderStatus().toString());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Re-throw the exception to signal an error.
        }
    }

    public Order getPendingOrderByUserID(String userId, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT orderID FROM Orders WHERE userId=?, status=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);

            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, "pending");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Print each book's information in the specified format

                Order order = new Order(resultSet.getInt("orderID"),
                        resultSet.getString("userId"),
                        resultSet.getDate("issueDate"),
                        resultSet.getDouble("totalCost"),
                        resultSet.getString("status"));
                System.out.println("{" + "Pending Order" +
                        "OrderId='" + resultSet.getInt("OrderID") + "'" +
                        ", userID='" + resultSet.getString("UserID") + "'" +
                        ", DATE='" + resultSet.getDate("IssueDate") + "'" +
                        ", cost='" + resultSet.getDouble("TotalCost") + "'" +
                        ",status'" + resultSet.getString("Status") + "'" +
                        "}");

                return order;
            } else {
                System.out.println("No order to be found ");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public void addOrderLine(OrderLine orderLine, Connection connection) throws SQLException {
        try {
            // Create an SQL INSERT statement
            String insertSQL = "INSERT INTO Order_Line (orderLineID, orderID, productID, quantity, lineCost) VALUES (?, ?, ?, ?, ?)";

            // Prepare and execute the INSERT statement
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, orderLine.getOrderLineID());
            preparedStatement.setInt(2, orderLine.getOrderID());
            preparedStatement.setInt(3, orderLine.getProductID());
            preparedStatement.setInt(4, orderLine.getQuantity());
            preparedStatement.setDouble(5, orderLine.getLineCost());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Re-throw the exception to signal an error.
        }
    }

    public void updateDeclineOrder(int orderId, Connection connection) throws SQLException{

        try {
            String updateSQL = "UPDATE Orders SET declined=? WHERE orderId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, orderId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully.");
            } else {
                System.out.println("No rows were updated for userId: ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public Order getOrder(int orderId, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Orders WHERE orderId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);

            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Print each book's information in the specified format

                Order order = new Order(resultSet.getInt("orderID"),
                        resultSet.getString("userId"),
                        resultSet.getDate("issueDate"),
                        resultSet.getDouble("totalCost"),
                        resultSet.getString("status"));
                System.out.println("{" +
                        "OrderId='" + resultSet.getInt("OrderID") + "'" +
                        ", userID='" + resultSet.getString("UserID") + "'" +
                        ", DATE='" + resultSet.getDate("IssueDate") + "'" +
                        ", cost='" + resultSet.getDouble("TotalCost") + "'" +
                        ",status'" + resultSet.getString("Status") + "'" +
                        "}");

                return order;
            } else {
                System.out.println("No order to be found ");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public boolean isDeclinedOrder(int orderId, Connection connection) throws SQLException{
        try{
        String selectSQL = "SELECT declined FROM Orders WHERE orderId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);

            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            
                if(resultSet.getBoolean("declined")){
                    return true;

                }else {return false;}
                            
            } else {
                return false;              
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public boolean isBlockedOrder(Order order, Connection connection) throws SQLException{
        System.out.println("blocked funciton");
        
        try{
            for (OrderLine orderLine : getAllOrdersLinesByOrder(order.getOrderID(), connection)) {
                int stockQuantity = getQuantitybyProductID(orderLine.getProductID(), connection);
                if (stockQuantity - orderLine.getQuantity()< 0){
                    System.out.println(stockQuantity - orderLine.getQuantity());
                    return true;
                }
            }
            return false;
            
        }catch (SQLException e){
            e.printStackTrace();
            throw e;
        }      
    }

    public void updateOrderLineCost(double cost, int orderlineId, Connection connection) throws SQLException{
        try {
            String updateSQL = "UPDATE Order_Line SET lineCost=? WHERE orderLineID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);


            preparedStatement.setDouble(1, cost);
            preparedStatement.setInt(2, orderlineId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully.");
            } else {
                System.out.println("No rows were updated for userId: ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void updateQuantity(int productId, int quantity, Connection connection) throws SQLException {
        try {
            String updateSQL = "UPDATE Inventory SET Quantity=? WHERE ProductID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully.");
            } else {
                System.out.println("No rows were updated for userId: ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void updateOrderLineQuantity(int orderLineId, int quantity, Connection connection) throws SQLException{
        try {
            String updateSQL = "UPDATE Order_Line SET quantity=? WHERE orderLineID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, orderLineId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully.");
            } else {
                System.out.println("No rows were updated for userId: ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void updateOrderStatus(int orderId, String status, Connection connection) throws SQLException {

        try {
            String updateSQL = "UPDATE Orders SET status=? WHERE orderId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, orderId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully.");
            } else {
                System.out.println("No rows were updated for userId: ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public Order[] getAllOrdersByUser(String userId, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Orders WHERE userId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> orderList = new ArrayList<>();

            while (resultSet.next()) {

                Order order = new Order(resultSet.getInt("orderID"),
                        resultSet.getString("userId"),
                        resultSet.getDate("issueDate"),
                        resultSet.getDouble("totalCost"),
                        resultSet.getString("status"));

                System.out.println("{" +
                        "OrderId='" + resultSet.getInt("OrderID") + "'" +
                        ", userID='" + resultSet.getString("UserID") + "'" +
                        ", DATE='" + resultSet.getDate("IssueDate") + "'" +
                        ", cost='" + resultSet.getDouble("TotalCost") + "'" +
                        ",status'" + resultSet.getString("Status") + "'" +
                        "}");
                orderList.add(order);

            }

            return orderList.toArray(new Order[orderList.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    public OrderLine[] getAllOrdersLinesByOrder(int orderID, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Order_Line WHERE orderID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, orderID);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<OrderLine> orderList = new ArrayList<>();

            while (resultSet.next()) {

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
                        ", LineCost='" + resultSet.getDouble("lineCost") + "'" +
                        "}");
                orderList.add(orderline);

            }

            return orderList.toArray(new OrderLine[orderList.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    public String getProductCode(int productID, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT productCo FROM Product WHERE productID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, productID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("productCo");
            } else {
                return "Not Found";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String getProductBrand(int productID, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT brandName FROM Product WHERE productID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, productID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("brandName");
            } else {
                return "Not Found";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String getProductName(int productID, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT productName FROM Product WHERE productID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, productID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("productName");
            } else {
                return "Not Found";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Double getProductCost (int productId, Connection connection) throws SQLException{
         try {
            String selectSQL = "SELECT retailPrice FROM Product WHERE productID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("retailPrice");
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void insertOrder(Order order, Connection connection) throws SQLException {
        try {
            String insertSQL = "INSERT INTO Orders (orderId, userId, issueDate, totalCost, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setInt(1, order.getOrderID());
            preparedStatement.setString(2, order.getUserID());
            preparedStatement.setDate(3, new java.sql.Date(order.getIssueDate().getTime()));
            preparedStatement.setDouble(4, order.getTotalCost());
            preparedStatement.setObject(5, order.getOrderStatus());
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void insertOrderLine(OrderLine line, Connection connection) throws SQLException {
        try {
            String insertSQL = "INSERT INTO Order_line (orderLineID, orderID, productID, quantity, lineCost) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setInt(1, line.getOrderLineID());
            preparedStatement.setInt(2, line.getOrderID());
            preparedStatement.setInt(3, line.getProductID());
            preparedStatement.setInt(4, line.getQuantity());
            preparedStatement.setDouble(5, line.getLineCost());

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;

        }
    }

    public String getUserIDbyOrderID(int orderID, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT userId FROM Orders WHERE orderId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, orderID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("userId");
            } else {
                return "Not Found";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int getQuantitybyProductID(int productID, Connection connection) throws SQLException {

        try {
            String selectSQL = "SELECT Quantity FROM Inventory WHERE productID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, productID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("Quantity");
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    public void deleteOrder(int orderId, Connection connection) throws SQLException {
        try {
            String deleteSQL = "DELETE FROM Orders WHERE orderID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, orderId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) deleted successfully.");
            } else {
                System.out.println("No rows were deleted for userId: " + orderId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }

    }

}
