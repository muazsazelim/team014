package com.sheffield.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sheffield.model.DatabaseConnectionHandler;
//import com.sheffield.model.DatabaseOperations;
import com.sheffield.model.user.User;

public class TestOperations {

    // Insert a new book into the database
    public void insertUser(User newUser, Connection connection) throws SQLException {
        try {
            // Create an SQL INSERT statement
            String insertSQL = "INSERT INTO Users (userId, email, username, password_hash, failed_login_attempts, account_locked) VALUES (?, ?, ?, ?, ?, ?)";

            // Prepare and execute the INSERT statement
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, newUser.getuserId());
            preparedStatement.setString(2, newUser.getemail());
            preparedStatement.setString(3, newUser.getusername());
            preparedStatement.setString(4, newUser.getPasswordHash());
            preparedStatement.setInt(5, newUser.getFailedLoginAttempt());
            preparedStatement.setBoolean(6, newUser.getaccountLocked());

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
                        ", username='" + resultSet.getString("username") + "'" +
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

    // Get a user by username
    public void getUserByusername(String username, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Users WHERE username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("<==================== User BY username =====================>");
            if (resultSet.next()) {

                System.out.println("{" +
                        "userId='" + resultSet.getString("userId") + "'" +
                        ", email='" + resultSet.getString("email") + "'" +
                        ", username='" + resultSet.getString("username") + "'" +
                        ", passwordHash='" + resultSet.getString("password_hash") + "'" +
                        ", failed_login_attempts='" + resultSet.getInt("failed_login_attempts") + "'" +
                        ", account_locked='" + resultSet.getBoolean("account_locked") + "'" +
                        ", forename='" + resultSet.getString("forename") + "'" +
                        ", surname='" + resultSet.getString("surname") + "'" +
                        "}");
            } else {
                System.out.println("User with username " + username + " not found.");
            }
            System.out.println("<=======================================================>");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public String getForenameByUsername(String username, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Users WHERE username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, username);
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

    public String getSurnameByUsername(String username, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Users WHERE username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, username);
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

    public String getEmailByUsername(String username, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Users WHERE username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, username);
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

    // Update an existing book in the database
    public void updateUser(User newUser, Connection connection) throws SQLException {
        try {
            String updateSQL = "UPDATE Users SET email=?, username=?,"+
            "password_hash=?, failed_login_attempts=?, account_locked=? WHERE userId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, newUser.getemail());
            preparedStatement.setString(2, newUser.getusername());
            preparedStatement.setString(3, newUser.getPasswordHash());
            preparedStatement.setInt(4, newUser.getFailedLoginAttempt());
            preparedStatement.setBoolean(5, newUser.getaccountLocked());
            preparedStatement.setString(6, newUser.getuserId());

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

            // // Adding a book to the database.
            User user1 = new User(UniqueUserIDGenerator.generateUniqueUserID(), "shawnspencer@example.com", "ShawnSpencer", "3d1ae7ee74752fc7b3808ea93e69bf35e73d7ad8bd759bd53e2204076a87ed7a", 0, false);
            databaseOperations.insertUser(user1, databaseConnectionHandler.getConnection());

            // Retrieving all the books and printing them out.
            databaseOperations.getAllUsers(databaseConnectionHandler.getConnection());

        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            databaseConnectionHandler.closeConnection();
        }

    }
}
