package com.sheffield.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sheffield.util.TestOperations;
import com.sheffield.model.user.User;


public class ManagerView extends JFrame {

    public ManagerView (Connection connection) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Train of Sheffield");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320,320);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        this.add(panel);

        JFrame parent = this;

        parent.getContentPane().setLayout(new BorderLayout());
        panel.setLayout(new GridLayout(0,2));

        
        // Create buttons that links to other pages from default page
        JLabel managerView;
        managerView = new JLabel("Manager View");

        // Add components to the panel
        parent.getContentPane().add(managerView, BorderLayout.NORTH);

        TestOperations testOperations = new TestOperations();

        ArrayList<User> users = new ArrayList<User>(testOperations.getAllUsersObj(connection));
        
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserType().equals("staff")) {
                panel.add(new JLabel("First Name - " + users.get(i).getForename() + ", Surname - " + users.get(i).getSurname() + ", Email - " +users.get(i).getemail()));
                panel.add(new JButton("Demote"));
            }
        }

        parent.getContentPane().add(panel, BorderLayout.CENTER);
        parent.pack();

    }
    
}
