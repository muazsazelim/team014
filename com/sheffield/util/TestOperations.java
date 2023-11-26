package com.sheffield.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sheffield.model.Address;
import com.sheffield.model.DatabaseConnectionHandler;
import com.sheffield.model.user.User;
import com.sheffield.model.order.Order;



public class TestOperations {

    // Insert a new book into the database
    public void insertUser(User newUser, Connection connection) throws SQLException {
        try {
            // Create an SQL INSERT statement
            String insertSQL = "INSERT INTO Users (userId, email, password_hash, failed_login_attempts, account_locked, forename, surname) VALUES (?, ?, ?, ?, ?, ?, ?)";

            // Prepare and execute the INSERT statement
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, newUser.getuserId());
            preparedStatement.setString(2, newUser.getemail());
            preparedStatement.setString(3, newUser.getPasswordHash());
            preparedStatement.setInt(4, newUser.getFailedLoginAttempt());
            preparedStatement.setBoolean(5, newUser.getaccountLocked());
            preparedStatement.setString(6, newUser.getForename());
            preparedStatement.setString(7, newUser.getSurname());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Re-throw the exception to signal an error.
        }
    }

    // Get all books from the database
    public void getAllUsers(Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Users";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("<=================== GET ALL Current Users ====================>");
            while (resultSet.next()) {
                // Print each book's information in the specified format
                System.out.println("{" +
                        "userId='" + resultSet.getString("userId") + "'" +
                        ", email='" + resultSet.getString("email") + "'" +
                        ", passwordHash='" + resultSet.getString("password_hash") + "'" +
                        ", failed login attempts='" + resultSet.getInt("failed_login_attempts") + "'" +
                        ", account locked='" + resultSet.getBoolean("account_locked") + "'" +
                        ", forename='" + resultSet.getString("forename") + "'" +
                        ", surname='" + resultSet.getString("surname") + "'" +
                        "}");
            }
            System.out.println("<======================================================>");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public ArrayList getAllUsersObj(Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Users";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("<=================== GET ALL Current Users ====================>");
            ArrayList users = new ArrayList<User>();
            while (resultSet.next()) {

                User newUser = new User(resultSet.getString("userId"), resultSet.getString("email"),resultSet.getString("password_hash"), resultSet.getInt("failed_login_attempts"), resultSet.getBoolean("account_locked"), resultSet.getString("forename"),resultSet.getString("surname"),resultSet.getString("usertype"));
                System.out.println("{" +
                        "userId='" + resultSet.getString("userId") + "'" +
                        ", email='" + resultSet.getString("email") + "'" +
                        ", passwordHash='" + resultSet.getString("password_hash") + "'" +
                        ", failed login attempts='" + resultSet.getInt("failed_login_attempts") + "'" +
                        ", account locked='" + resultSet.getBoolean("account_locked") + "'" +
                        ", forename='" + resultSet.getString("forename") + "'" +
                        ", surname='" + resultSet.getString("surname") + "'" +
                        "}");
                users.add(newUser);
            }
            System.out.println("<======================================================>");
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    // Get a user by forename
    public void getUserByforename(String forename, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Users WHERE forename=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, forename);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("<==================== User BY forename =====================>");
            if (resultSet.next()) {

                System.out.println("{" +
                        "userId='" + resultSet.getString("userId") + "'" +
                        ", email='" + resultSet.getString("email") + "'" +
                        ", passwordHash='" + resultSet.getString("password_hash") + "'" +
                        ", failed_login_attempts='" + resultSet.getInt("failed_login_attempts") + "'" +
                        ", account_locked='" + resultSet.getBoolean("account_locked") + "'" +
                        ", forename='" + resultSet.getString("forename") + "'" +
                        ", surname='" + resultSet.getString("surname") + "'" +
                        "}");
            } else {
                System.out.println("User with forename " + forename + " not found.");
            }
            System.out.println("<=======================================================>");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public User getUser(String email, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Users WHERE email=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("<==================== User BY Email =====================>");
            if (resultSet.next()) {
                User user = new User(resultSet.getString("userId"), resultSet.getString("email"),resultSet.getString("password_hash"), resultSet.getInt("failed_login_attempts"), resultSet.getBoolean("account_locked"), resultSet.getString("forename"),resultSet.getString("surname"), resultSet.getString("usertype"));
                System.out.println("{" +
                        "userId='" + resultSet.getString("userId") + "'" +
                        ", email='" + resultSet.getString("email") + "'" +
                        ", passwordHash='" + resultSet.getString("password_hash") + "'" +
                        ", failed_login_attempts='" + resultSet.getInt("failed_login_attempts") + "'" +
                        ", account_locked='" + resultSet.getBoolean("account_locked") + "'" +
                        ", forename='" + resultSet.getString("forename") + "'" +
                        ", surname='" + resultSet.getString("surname") + "'" +
                        "}");
                return user;
            } else {
                System.out.println("User with email " + email + " not found.");
                return null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public String getEmail(String userID, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Users WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                return resultSet.getString("email");
            } else {
                return "Not Found";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public String getForename(String userID, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Users WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("forename");
            } else {
                return "Not Found";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public String getSurname(String userID, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Users WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                return resultSet.getString("surname");
            } else {
                return "Not Found";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public Address getAddress(String userID, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Users INNER JOIN Address ON Users.houseID=Address.houseID WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Address userAddress = new Address(resultSet.getInt("houseID"), resultSet.getString("houseNumber"), resultSet.getString("roadName"), resultSet.getString("cityName"),  resultSet.getString("postcode"));
                System.out.println(userAddress.toString());
                return userAddress;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }


    // Update an existing book in the database
    public void updateUser(User newUser, Connection connection) throws SQLException {
        try {
            String updateSQL = "UPDATE Users SET email=?,"+
            "password_hash=?, failed_login_attempts=?, account_locked=?, forename=?, surname=? WHERE userId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, newUser.getemail());
            preparedStatement.setString(2, newUser.getPasswordHash());
            preparedStatement.setInt(3, newUser.getFailedLoginAttempt());
            preparedStatement.setBoolean(4, newUser.getaccountLocked());
            preparedStatement.setString(7, newUser.getuserId());
            preparedStatement.setString(5, newUser.getForename());
            preparedStatement.setString(6, newUser.getSurname());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully.");
            } else {
                System.out.println("No rows were updated for userId: " + newUser.getuserId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public void updateEmail(String userID, String email, Connection connection) throws SQLException {
        try {
            String updateSQL = "UPDATE Users SET email=?" +
            "WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, userID);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully.");
            } else {
                System.out.println("No rows were updated for userId: " + userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public void updateFName(String userID, String fname, Connection connection) throws SQLException {
        try {
            String updateSQL = "UPDATE Users SET forename=?" +
            "WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, fname);
            preparedStatement.setString(2, userID);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully.");
            } else {
                System.out.println("No rows were updated for userId: " + userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public void updateSName(String userID, String sname, Connection connection) throws SQLException {
        try {
            String updateSQL = "UPDATE Users SET surname=?" +
            "WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, sname);
            preparedStatement.setString(2, userID);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully.");
            } else {
                System.out.println("No rows were updated for userId: " + userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public void promoteUserByEmail(String email, Connection connection) throws SQLException {
        try {
            String updateSQL = "UPDATE Users SET usertype='staff'" +
            "WHERE email=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, email);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully.");
            } else {
                System.out.println("No rows were updated for userId: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public void demoteUser(String userID, Connection connection) throws SQLException {
        try {
            String updateSQL = "UPDATE Users SET usertype='customer'" +
            "WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, userID);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully.");
            } else {
                System.out.println("No rows were updated for userId: " + userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    // Delete a user from the database by userId
    public void deleteUser(String userId, Connection connection) throws SQLException {
        try {
            String deleteSQL = "DELETE FROM Users WHERE userId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, userId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) deleted successfully.");
            } else {
                System.out.println("No rows were deleted for userId: " + userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

   

    public static void main(String[] args) {
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        TestOperations databaseOperations = new TestOperations();
        try {
            databaseConnectionHandler.openConnection();

            // // Adding a user to the database.
            /*
            User user1 = new User(UniqueUserIDGenerator.generateUniqueUserID(), "shawnspencer@example.com", "ShawnSpencer", "3d1ae7ee74752fc7b3808ea93e69bf35e73d7ad8bd759bd53e2204076a87ed7a", 0, false);
            databaseOperations.insertUser(user1, databaseConnectionHandler.getConnection());
            */

            // Retrieving all the books and printing them out.
            databaseOperations.getAllUsers(databaseConnectionHandler.getConnection());

        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            databaseConnectionHandler.closeConnection();
        }

    }
}
