package com.sheffield.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import com.sheffield.util.TestOperations;


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

        JFrame parent = this;

        parent.getContentPane().setLayout(new BorderLayout());
        panel.setLayout(new GridLayout(0,1));

        
        // Create buttons that links to other pages from default page
        userDetails = new JLabel("Change Details");

        // Create JLabels for username and password
        JLabel usernameLabel = new JLabel("  Username:");
        JTextField usernameField = new JTextField(20);

        // Create JTextFields for entering username and password


        // Create a JButton for the login action
        JButton searchButton = new JButton("Search");        

        // Add components to the panel
        panel.add(userDetails);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(searchButton);

        parent.getContentPane().add(panel, BorderLayout.NORTH);
        parent.pack();

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TestOperations testOperations = new TestOperations();
                try {
                    panel.add(new JLabel("Email - "+testOperations.getEmailByUsername(usernameField.getText(),connection)));
                    JTextField updateEmail = new JTextField();
                    panel.add(updateEmail);
                    JButton updateEmailButton = new JButton("Update Email");
                    panel.add(updateEmailButton);

                    updateEmailButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                testOperations.updateEmailUsername(usernameField.getText(), updateEmail.getText(), connection);
                            } catch (SQLException error) {
                                System.out.println("SQL Error");
                            }
                        }
                    });

                    panel.add(new JLabel("Forename - "+testOperations.getForenameByUsername(usernameField.getText(),connection)));
                    JTextField updateFName = new JTextField();
                    panel.add(updateFName);
                    JButton updateFNameButton = new JButton("Update Name");
                    panel.add(updateFNameButton);

                    updateFNameButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                testOperations.updateFNameUsername(usernameField.getText(), updateFName.getText(), connection);
                            } catch (SQLException error) {
                                System.out.println("SQL Error");
                            }
                        }
                    });
                    panel.add(new JLabel("Surname - "+testOperations.getSurnameByUsername(usernameField.getText(),connection)));
                    JTextField updateSName = new JTextField();
                    panel.add(updateSName);
                    JButton updateSNameButton = new JButton("Update Name");
                    panel.add(updateSNameButton);

                    updateSNameButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                testOperations.updateSNameUsername(usernameField.getText(), updateSName.getText(), connection);
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
        });

    }
    
}
