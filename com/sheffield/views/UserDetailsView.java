package com.sheffield.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import com.sheffield.util.TestOperations;


public class UserDetailsView extends JFrame {

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
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        
        // Create buttons that links to other pages from default page
        JLabel changeDetails;
        changeDetails = new JLabel("Change Details");

        // Create JLabels for username and password
        JLabel usernameLabel = new JLabel("  Username:");
        JTextField usernameField = new JTextField(20);

        // Create JTextFields for entering username and password


        // Create a JButton for the login action
        JButton searchButton = new JButton("Search");   
        
        JPanel userDetails = new JPanel();
        userDetails.setLayout(new BoxLayout(userDetails, BoxLayout.PAGE_AXIS));

        // Add components to the panel
        panel.add(changeDetails);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(searchButton);
        panel.add(userDetails);
        

        parent.getContentPane().add(panel, BorderLayout.NORTH);
        parent.pack();

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TestOperations testOperations = new TestOperations();
                try {
                    userDetails.removeAll();
                    
                    JLabel userEmail = new JLabel("Email - "+testOperations.getEmailByUsername(usernameField.getText(),connection));
                    userDetails.add(userEmail);
                    JTextField updateEmail = new JTextField();
                    userDetails.add(updateEmail);
                    JButton updateEmailButton = new JButton("Update Email");
                    userDetails.add(updateEmailButton);

                    updateEmailButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                testOperations.updateEmailUsername(usernameField.getText(), updateEmail.getText(), connection);
                                updateEmail.setText("");
                                userEmail.setText("Email - "+testOperations.getEmailByUsername(usernameField.getText(),connection));
                                userEmail.revalidate();
                            } catch (SQLException error) {
                                System.out.println("SQL Error");
                            }
                        }
                    });

                    JLabel userFname = new JLabel("Forename - "+testOperations.getForenameByUsername(usernameField.getText(),connection)); 
                    userDetails.add(userFname);
                    JTextField updateFName = new JTextField();
                    userDetails.add(updateFName);
                    JButton updateFNameButton = new JButton("Update Name");
                    userDetails.add(updateFNameButton);

                    updateFNameButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                testOperations.updateFNameUsername(usernameField.getText(), updateFName.getText(), connection);
                                updateFName.setText("");
                                userFname.setText("Forename - "+testOperations.getForenameByUsername(usernameField.getText(),connection));
                                userFname.revalidate();
                            } catch (SQLException error) {
                                System.out.println("SQL Error");
                            }
                        }
                    });

                    JLabel userSname = new JLabel("Surname - "+testOperations.getSurnameByUsername(usernameField.getText(),connection)); 
                    userDetails.add(userSname);
                    JTextField updateSName = new JTextField();
                    userDetails.add(updateSName);
                    JButton updateSNameButton = new JButton("Update Name");
                    userDetails.add(updateSNameButton);

                    updateSNameButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                testOperations.updateSNameUsername(usernameField.getText(), updateSName.getText(), connection);
                                updateSName.setText("");
                                userSname.setText("Surname - "+testOperations.getSurnameByUsername(usernameField.getText(),connection));
                                userSname.revalidate();
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
