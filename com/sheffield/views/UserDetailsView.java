package com.sheffield.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import com.sheffield.util.TestOperations;
import com.sheffield.model.user.User;


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
        try {
            userDetails.removeAll();
            
            JLabel userEmail = new JLabel("Email - "+testOperations.getEmailByUsername(user.getusername(),connection));
            userDetails.add(userEmail);
            JTextField updateEmail = new JTextField();
            userDetails.add(updateEmail);

            JLabel userFname = new JLabel("Forename - "+testOperations.getForenameByUsername(user.getusername(),connection)); 
            userDetails.add(userFname);
            JTextField updateFName = new JTextField();
            userDetails.add(updateFName);

            JLabel userSname = new JLabel("Surname - "+testOperations.getSurnameByUsername(user.getusername(),connection)); 
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
                            testOperations.updateEmailUsername(user.getusername(), updateEmail.getText(), connection);
                            updateEmail.setText("");
                            userEmail.setText("Email - "+testOperations.getEmailByUsername(user.getusername(),connection));
                            userEmail.revalidate();
                        }
                        if (!updateFName.getText().equals("")) {
                            testOperations.updateFNameUsername(user.getusername(), updateFName.getText(), connection);
                            updateFName.setText("");
                            userFname.setText("Forename - "+testOperations.getForenameByUsername(user.getusername(),connection));
                            userFname.revalidate();
                        }
                        if (!updateSName.getText().equals("")) {
                            testOperations.updateSNameUsername(user.getusername(), updateSName.getText(), connection);
                            updateSName.setText("");
                            userSname.setText("Surname - "+testOperations.getSurnameByUsername(user.getusername(),connection));
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
