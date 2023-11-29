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


public class UserDetailsView extends JPanel {

    public UserDetailsView (Connection connection, User user) throws SQLException {

        // JFrame parent = this;
        // parent.setTitle("Train of Sheffield");
        // parent.getContentPane().setLayout(new BorderLayout());
        // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // parent.setVisible(true);
        // parent.setSize(720,600);

        // Create a JPanel to hold the components
        JPanel contentPanel = this;
        contentPanel.setLayout(new BorderLayout());
        //parent.add(contentPanel, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        contentPanel.add(panel, BorderLayout.CENTER);


        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        
        // Create buttons that links to other pages from default page
        JLabel changeDetails;
        changeDetails = new JLabel("Change Details"); 
        
        JPanel userDetails = new JPanel();
        userDetails.setLayout(new BoxLayout(userDetails, BoxLayout.PAGE_AXIS));

        // Add components to the panel
        panel.add(changeDetails);
        panel.add(userDetails);
        

        TestOperations testOperations = new TestOperations();
        Address userAddress = testOperations.getAddress(user.getuserId(), connection);


            
        JLabel userEmail = new JLabel("Email - "+user.getemail());
        userDetails.add(userEmail);
        JTextField updateEmail = new JTextField();
        userDetails.add(updateEmail);

        JLabel userFname = new JLabel("Forename - "+user.getForename()); 
        userDetails.add(userFname);
        JTextField updateFName = new JTextField();
        userDetails.add(updateFName);

        JLabel userSname = new JLabel("Surname - "+user.getSurname()); 
        userDetails.add(userSname);
        JTextField updateSName = new JTextField();
        userDetails.add(updateSName);

        JLabel userhNumber = new JLabel("House Number - "+ userAddress.getHouseNumber()); 
        userDetails.add(userhNumber);
        JTextField updateHouseNumber = new JTextField();
        userDetails.add(updateHouseNumber);

        JLabel userRName = new JLabel("Road Name - "+ userAddress.getRoadName()); 
        userDetails.add(userRName);
        JTextField updateRName = new JTextField();
        userDetails.add(updateRName);

        JLabel userCName = new JLabel("City Name - "+ userAddress.getCityName()); 
        userDetails.add(userCName);
        JTextField updateCName = new JTextField();
        userDetails.add(updateCName);

        JLabel userPostcode = new JLabel("Postcode - "+ userAddress.getPostcode()); 
        userDetails.add(userPostcode);
        JTextField updatePostcode = new JTextField();
        userDetails.add(updatePostcode);
        
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
                    }
                    if (!updateFName.getText().equals("")) {
                        testOperations.updateFName(user.getuserId(), updateFName.getText(), connection);
                        updateFName.setText("");
                        userFname.setText("Forename - "+testOperations.getForename(user.getuserId(),connection));
                    }
                    if (!updateSName.getText().equals("")) {
                        testOperations.updateSName(user.getuserId(), updateSName.getText(), connection);
                        updateSName.setText("");
                        userSname.setText("Surname - "+testOperations.getSurname(user.getuserId(),connection));
                    }                    
                
                    Address updatedAddress = testOperations.getAddress(user.getuserId(), connection);
                    if (!updateHouseNumber.getText().equals("")) 
                    {
                        updatedAddress.setHouseNumber(updateHouseNumber.getText());
                        updateHouseNumber.setText("");
                    }   
                    if (!updateRName.getText().equals("")) 
                    {
                        updatedAddress.setRoadName(updateRName.getText());
                        updateRName.setText("");
                    }   
                    if (!updateCName.getText().equals("")) 
                    {
                        updatedAddress.setCityName(updateCName.getText());
                        updateCName.setText("");
                    }   
                    if (!updatePostcode.getText().equals("")) 
                    {
                        updatedAddress.setPostcode(updatePostcode.getText());
                        updatePostcode.setText("");
                    }   
                
                    int addressID = testOperations.addressExists(updatedAddress.getHouseNumber(), updatedAddress.getPostcode(), connection);
                    if (addressID == -1) {
                        testOperations.insertAddress(updatedAddress, connection);
                        testOperations.updateUserAddress(user.getuserId(),testOperations.addressExists(updatedAddress.getHouseNumber(), updatedAddress.getPostcode(), connection), connection);
                    } else {
                        testOperations.updateUserAddress(user.getuserId(),addressID, connection);
                        testOperations.updateAddress(updatedAddress,connection);
                    }
                    

                    //testOperations.updateAddress(updatedAddress,connection);
                    updatedAddress = testOperations.getAddress(user.getuserId(), connection);

                    userhNumber.setText("House Number - "+updatedAddress.getHouseNumber());
                    userRName.setText("Road Name - "+updatedAddress.getRoadName());
                    userCName.setText("City Name - "+updatedAddress.getCityName());
                    userPostcode.setText("Postcode - "+updatedAddress.getPostcode());

                } catch (SQLException error) {
                    System.out.println("SQL Error");
                }

                userDetails.revalidate();
                userDetails.repaint();


            }
        });

        panel.revalidate();
        panel.repaint();    


    }
    
}
