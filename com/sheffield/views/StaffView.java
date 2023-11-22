package com.sheffield.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import com.sheffield.util.TestOperations;


public class StaffView extends JFrame {

    public StaffView (Connection connection) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Train of Sheffield");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320,320);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        this.add(panel);

        JFrame parent = this;

        parent.getContentPane().setLayout(new BorderLayout());
        panel.setLayout(new GridLayout(0,1));

        
        // Create buttons that links to other pages from default page
        JLabel staffView;
        staffView = new JLabel("Staff View");

        // Add components to the panel
        panel.add(staffView);

        parent.getContentPane().add(panel, BorderLayout.NORTH);
        parent.pack();

    }
    
}
