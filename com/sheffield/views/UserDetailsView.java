package com.sheffield.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;


public class UserDetailsView extends JFrame {
    private JLabel userDetails;

    public UserDetailsView (Connection connection) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Train of Sheffield");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320,320);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        this.add(panel);

        this.getContentPane().setLayout(new BorderLayout());
        panel.setLayout(new GridLayout(0,1));

        // Create buttons that links to other pages from default page
        userDetails = new JLabel("Change Details");
        

        // Add components to the panel
        panel.add(userDetails);

        this.getContentPane().add(panel, BorderLayout.NORTH);
        this.pack();

    }
    
}
