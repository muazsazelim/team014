package com.sheffield.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import com.sheffield.model.user.User;


import com.sheffield.util.TestOperations;


public class StaffView extends JPanel {

    public StaffView (Connection connection, User user) throws SQLException {

        
        JPanel contentPanel = this;
        contentPanel.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());

        JLabel staffView = new JLabel();
        staffView.setText("Staff View");
        staffView.setHorizontalAlignment(JLabel.CENTER);
        staffView.setVerticalAlignment(JLabel.TOP);

        JButton backButton = new JButton("Back");

        header.add(staffView, BorderLayout.NORTH);
        header.add(backButton, BorderLayout.WEST);
 

        // Create a JPanel to hold the components
        JPanel staffContent = new JPanel();
        staffContent.setLayout(new GridLayout(0,1));

        contentPanel.add(header, BorderLayout.NORTH);
        contentPanel.add(staffContent, BorderLayout.CENTER);


        JButton inventory = new JButton("Inventory");
        JButton orderManagement = new JButton("OrderManagement");

        staffContent.add(inventory);
        staffContent.add(orderManagement);

        inventory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InventoryView inventoryView = null;

                try {
                    inventoryView = new InventoryView(connection, user);

                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(inventoryView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();
                    
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
        });      
        
        orderManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderManagementStaffView orderManagementStaffView = null;

                try {
                    orderManagementStaffView = new OrderManagementStaffView(connection, user);

                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(orderManagementStaffView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();
                    
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
        });  
        


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to User Main View");

                //dispose();
                UserMainView userMainView = null;
                try {
                    userMainView = new UserMainView(connection, user);
                    //userDetailsView.setVisible(true);
                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(userMainView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
        });

   

    }
    
}
