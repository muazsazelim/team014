package com.sheffield.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sheffield.model.Address;
import com.sheffield.model.DatabaseConnectionHandler;
import com.sheffield.model.user.BankDetails;
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

    public String getEmail(String userId, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Users WHERE userId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, userId);
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

    public String getForename(String userId, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Users WHERE userId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, userId);
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

    public String getSurname(String userId, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Users WHERE userId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, userId);
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

    public void insertAddress(Address newAddress, Connection connection) throws SQLException {
        try {
            // Create an SQL INSERT statement
            String insertSQL = "INSERT INTO Address (houseNumber, roadName, cityName, postcode, userId) VALUES (?, ?, ?, ?, ?)";

            // Prepare and execute the INSERT statement
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, newAddress.getHouseNumber());
            preparedStatement.setString(2, newAddress.getRoadName());
            preparedStatement.setString(3, newAddress.getCityName());
            preparedStatement.setString(4, newAddress.getPostcode());
            preparedStatement.setString(5, newAddress.getUserId());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Re-throw the exception to signal an error.
        }
    }

    public Address getAddress(String userId, Connection connection) throws SQLException {
        try {
<<<<<<< HEAD
            String selectSQL = "SELECT * FROM Users INNER JOIN Address ON Users.houseNumber=Address.houseNumber WHERE userID=?";
=======
            String selectSQL = "SELECT * FROM Address WHERE userId=?";
>>>>>>> 5d594b6b94506df528ad004495f6cc0e8efb6745
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Address userAddress = new Address(resultSet.getString("houseNumber"), resultSet.getString("roadName"), resultSet.getString("cityName"),  resultSet.getString("postcode"), resultSet.getString("userId"));
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

    public int addressExists(String houseNumber, String postcode, Connection connection) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Address WHERE houseNumber=? AND postcode=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, houseNumber);
            preparedStatement.setString(2, postcode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("houseID");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public void updateAddress(Address address, Connection connection) throws SQLException {
        try {
            String updateSQL = "UPDATE Address SET houseNumber=?,"+
            "roadName=?, cityName=?, postcode=? WHERE userId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, address.getHouseNumber());
            preparedStatement.setString(2, address.getRoadName());
            preparedStatement.setString(3, address.getCityName());
            preparedStatement.setString(4, address.getPostcode());
            preparedStatement.setString(5, address.getUserId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully.");
            } else {
                System.out.println("No rows were updated for userId: " + address.getUserId());
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

    public void updateEmail(String userId, String email, Connection connection) throws SQLException {
        try {
            String updateSQL = "UPDATE Users SET email=?" +
            "WHERE userId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, userId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully.");
            } else {
                System.out.println("No rows were updated for userId: " + userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public void updateFName(String userId, String fname, Connection connection) throws SQLException {
        try {
            String updateSQL = "UPDATE Users SET forename=?" +
            "WHERE userId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, fname);
            preparedStatement.setString(2, userId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully.");
            } else {
                System.out.println("No rows were updated for userId: " + userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public void updateSName(String userId, String sname, Connection connection) throws SQLException {
        try {
            String updateSQL = "UPDATE Users SET surname=? WHERE userId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, sname);
            preparedStatement.setString(2, userId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully.");
            } else {
                System.out.println("No rows were updated for userId: " + userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public void updateUserAddress(String userId, int houseID, Connection connection) throws SQLException {
        try {
            String updateSQL = "UPDATE Users SET houseID=? WHERE userId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setInt(1, houseID);
            preparedStatement.setString(2, userId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully.");
            } else {
                System.out.println("No rows were updated for userId: " + userId);
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

    public void demoteUser(String userId, Connection connection) throws SQLException {
        try {
            String updateSQL = "UPDATE Users SET usertype='customer'" +
            "WHERE userId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, userId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully.");
            } else {
                System.out.println("No rows were updated for userId: " + userId);
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

    public void insertBankDetails(BankDetails newBankDetails, Connection connection) throws SQLException {
        try {
            // Create an SQL INSERT statement
            String insertSQL = "INSERT INTO Bank_Details (cardNumber, bankName, holderName, cardExpDate, secCode) VALUES (?, ?, ?, ?, ?)";

            // Prepare and execute the INSERT statement
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, newBankDetails.getcardNumber());
            preparedStatement.setString(2, newBankDetails.getbankName());
            preparedStatement.setString(3, newBankDetails.getholderName());
            preparedStatement.setString(4, newBankDetails.getcardExpDate());
            preparedStatement.setString(5, newBankDetails.getsecCode());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Re-throw the exception to signal an error.
        }
    }

    public boolean isUserHaveBankDetails (String userId, Connection connection) throws SQLException{
         try {
            String selectSQL = "SELECT cardNumber FROM Users WHERE userId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String result = resultSet.getString("cardNumber");
                
                if(result != null){
                   
                    return true;
                }else{return false;}
                
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
       
    }

   

    public static void main(String[] args) {
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        TestOperations databaseOperations = new TestOperations();
        try {
            databaseConnectionHandler.openConnection();

            // // Adding a user to the database.
            /*
            User user1 = new User(UniqueuserIdGenerator.generateUniqueuserId(), "shawnspencer@example.com", "ShawnSpencer", "3d1ae7ee74752fc7b3808ea93e69bf35e73d7ad8bd759bd53e2204076a87ed7a", 0, false);
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
