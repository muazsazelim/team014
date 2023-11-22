package com.sheffield.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.awt.*;

import javax.swing.FocusManager;
//import javax.swing.*;
import javax.swing.JFrame;

// import com.mysql.cj.protocol.ResultsetRow;
import com.sheffield.util.HashedPasswordGenerator;
import com.sheffield.views.UserMainView;

public class DatabaseOperations {

    public String verifyLogin(Connection connection, String username, char[] enteredPassword) {
        
        try{
            //Query to fetch information
            String sql = "SELECT userId, password_hash, failed_login_attempts, " + "account_locked FROM Users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                String userId = resultSet.getString("userId");
                String storedPasswordHash = resultSet.getString("password_hash");
                int failedLoginAttempts = resultSet.getInt("failed_login_attempts");
                boolean accountLocked = resultSet.getBoolean("account_locked");

                //Chech if user is locked
                if (accountLocked) {
                    return "Account is locked. Please contact support";
                } else {
                    //verify pass
                    if (verifyPassword(enteredPassword, storedPasswordHash)) {
                        //Update login time
                        sql = "UPDATE Users SET last_login = CURRENT_TIMESTAMP, " + "failed_login_attempts = 0 WHERE userId = ?";
                        statement = connection.prepareStatement(sql);
                        statement.setString(1, userId);
                        statement.executeUpdate();
                        
                        Frame currentFrame = JFrame.getFrames()[0];
                        currentFrame.dispose();

                        UserMainView userMainView = null;
                        try {
                            userMainView = new UserMainView(connection);
                            userMainView.setVisible(true);
            
                        } catch (Throwable t) {
                            throw new RuntimeException(t);
                        }
                        return "Login successful for user: "+ username;
                    } else {
                        //Incorrect pass
                        failedLoginAttempts ++;
                        sql = "UPDATE Users SET failed_login_attempts = ?, account_locked = ? WHERE userId = ?";
                        statement = connection.prepareStatement(sql);
                        statement.setInt(1, failedLoginAttempts);
                        statement.setString(3, userId);
                        statement.setBoolean(2, accountLocked);

                        // Set the account_locked if the failed attempt more than 3
                        if (failedLoginAttempts >= 3) {
                            statement.setBoolean(2, true);
                        }
                        statement.executeUpdate();

                        return "Incorrect password. Failed login attempts: " + failedLoginAttempts;
                    }
                }
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        }

        return "User Not Found.";
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
