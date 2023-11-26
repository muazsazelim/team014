package com.sheffield.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import com.sheffield.model.Address;
import com.sheffield.model.user.User;
import com.sheffield.util.TestOperations;


public class UserDetailsView extends JFrame {

    public UserDetailsView (Connection connection, User user) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Train of Sheffield");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320,320);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        this.add(panel);

        JFrame parent = this;

        parent.getContentPane().setLayout(new BorderLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        
        // Create buttons that links to other pages from default page
        JLabel changeDetails;
        changeDetails = new JLabel("Change Details"); 
        
        JPanel userDetails = new JPanel();
        userDetails.setLayout(new BoxLayout(userDetails, BoxLayout.PAGE_AXIS));

        // Add components to the panel
        panel.add(changeDetails);
        panel.add(userDetails);
        

        parent.getContentPane().add(panel, BorderLayout.NORTH);
        parent.pack();

        TestOperations testOperations = new TestOperations();
        Address userAddress = testOperations.getAddress(user.getuserId(), connection);

        try {
            userDetails.removeAll();
            
            JLabel userEmail = new JLabel("Email - "+testOperations.getEmail(user.getuserId(),connection));
            userDetails.add(userEmail);
            JTextField updateEmail = new JTextField();
            userDetails.add(updateEmail);

            JLabel userFname = new JLabel("Forename - "+testOperations.getForename(user.getuserId(),connection)); 
            userDetails.add(userFname);
            JTextField updateFName = new JTextField();
            userDetails.add(updateFName);

            JLabel userSname = new JLabel("Surname - "+testOperations.getSurname(user.getuserId(),connection)); 
            userDetails.add(userSname);
            JTextField updateSName = new JTextField();
            userDetails.add(updateSName);

            
            JButton updateButton = new JButton("Update");
            userDetails.add(updateButton);

            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if (!updateEmail.getText().equals("")) {
                            testOperations.updateEmail(user.getuserId(), updateEmail.getText(), connection);
                            updateEmail.setText("");
                            userEmail.setText("Email - "+testOperations.getEmail(user.getuserId(),connection));
                            userEmail.revalidate();
                        }
                        if (!updateFName.getText().equals("")) {
                            testOperations.updateFName(user.getuserId(), updateFName.getText(), connection);
                            updateFName.setText("");
                            userFname.setText("Forename - "+testOperations.getForename(user.getuserId(),connection));
                            userFname.revalidate();
                        }
                        if (!updateSName.getText().equals("")) {
                            testOperations.updateSName(user.getuserId(), updateSName.getText(), connection);
                            updateSName.setText("");
                            userSname.setText("Surname - "+testOperations.getSurname(user.getuserId(),connection));
                            userSname.revalidate();
                        }
                    } catch (SQLException error) {
                        System.out.println("SQL Error");
                    }
                }
            });

            panel.revalidate();
            panel.repaint();
            parent.pack();
            
        } catch (SQLException error) {
            System.out.println("SQL Error");
        }

    }
    
}
