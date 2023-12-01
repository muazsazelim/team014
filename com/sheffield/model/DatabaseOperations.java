package com.sheffield.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.awt.*;

import javax.swing.FocusManager;
import javax.swing.JFrame;

import com.sheffield.util.HashedPasswordGenerator;
import com.sheffield.util.TestOperations;
import com.sheffield.views.UserMainView;

public class DatabaseOperations {

    public boolean verifyLogin(Connection connection, String email, char[] enteredPassword) {
        
        try{
            //Query to fetch information
            String sql = "SELECT userId, password_hash, failed_login_attempts, " + "account_locked FROM Users WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                String userId = resultSet.getString("userId");
                String storedPasswordHash = resultSet.getString("password_hash");
                int failedLoginAttempts = resultSet.getInt("failed_login_attempts");
                boolean accountLocked = resultSet.getBoolean("account_locked");

                //Chech if user is locked
                if (accountLocked) {
                    System.out.println("Account is locked. Please contact support");
                    return false;
                } else {
                    //verify pass
                    if (verifyPassword(enteredPassword, storedPasswordHash)) {
                        //Update login time
                        sql = "UPDATE Users SET last_login = CURRENT_TIMESTAMP, " + "failed_login_attempts = 0 WHERE userId = ?";
                        statement = connection.prepareStatement(sql);
                        statement.setString(1, userId);
                        statement.executeUpdate();
                        
                        
                        System.out.println("Login successful for user: "+ email);
                        return true;
                    } else {
                        //Incorrect pass
                        failedLoginAttempts ++;
                        sql = "UPDATE Users SET failed_login_attempts = ?, account_locked = ? WHERE userId = ?";
                        statement = connection.prepareStatement(sql);
                        statement.setInt(1, failedLoginAttempts);
                        statement.setString(3, userId);
                        statement.setBoolean(2, accountLocked);

                        
                        statement.executeUpdate();

                        System.out.println("Incorrect password. Failed login attempts: " + failedLoginAttempts);
                        return false;
                    }
                }
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println("User Not Found.");
        return false;
    }

    private static boolean verifyPassword(char[] enteredPassword, String storedPasswordHash) {
        try {
            String hashedEnteredPassword = HashedPasswordGenerator.hashPassword(enteredPassword);
            return hashedEnteredPassword.equals(storedPasswordHash);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
