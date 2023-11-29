package com.sheffield.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import com.sheffield.util.TestOperations;


public class StaffView extends JPanel {

    public StaffView (Connection connection) throws SQLException {

        
        JPanel contentPanel = this;
        contentPanel.setLayout(new BorderLayout());

 

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,1));


        contentPanel.add(panel, BorderLayout.CENTER);

        
        // Create buttons that links to other pages from default page
        JLabel staffView = new JLabel();
        staffView.setText("Staff View");
        staffView.setHorizontalAlignment(JLabel.CENTER);
        staffView.setVerticalAlignment(JLabel.TOP);


        // Add components to the panel
        panel.add(staffView);

    }
    
}
