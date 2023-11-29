package com.sheffield.views;

import javax.swing.*;

import com.sheffield.model.user.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;


public class UserMainView extends JFrame {
    
    public UserMainView (Connection connection, User user) throws SQLException {

        JFrame parent = this;
        parent.setTitle("Train of Sheffield");
        parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        parent.getContentPane().setLayout(new BorderLayout());
        parent.setVisible(true);
        parent.setSize(720,600);


        // Create a JPanel to hold the components
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        parent.add(contentPanel, BorderLayout.CENTER);

        JPanel navigation = new JPanel();
        navigation.setLayout(new GridLayout(0,1));


        contentPanel.add(navigation, BorderLayout.CENTER);


        // Create buttons that links to other pages from default page

        JButton userDetails;
        JButton orderHistory;
        JButton products;
        JButton staff;
        JButton manager;

        userDetails = new JButton("Change Details");
        orderHistory = new JButton("Order History");
        products = new JButton("View Products");

        navigation.add(userDetails);
        navigation.add(orderHistory);
        navigation.add(products);
        
        if (user.getUserType().equals("staff")){
            staff = new JButton("Staff");
            navigation.add(staff);

            staff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Staff Page");

                dispose();
                StaffView staffView = null;
                try {
                    staffView = new StaffView(connection);
                    staffView.setVisible(true);
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
            });

        }
        if (user.getUserType().equals("manager")){
            staff = new JButton("Staff");
            manager = new JButton("Manager");

            navigation.add(staff);
            navigation.add(manager);

            staff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Staff Page");

                dispose();
                StaffView staffView = null;
                try {
                    staffView = new StaffView(connection);
                    staffView.setVisible(true);
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
            });

            manager.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Manager Page");

                dispose();
                ManagerView managerView = null;
                try {
                    managerView = new ManagerView(connection);
                    managerView.setVisible(true);
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
            });

        }
        // Create an ActionListener for the view buttons
        userDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to User Details Page");

                //dispose();
                UserDetailsView userDetailsView = null;
                try {
                    userDetailsView = new UserDetailsView(connection, user);
                    //userDetailsView.setVisible(true);
                    contentPanel.removeAll();
                    contentPanel.add(userDetailsView, BorderLayout.CENTER);
                    contentPanel.revalidate();
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
        });
        
        orderHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //dispose();
                OrderHistoryView orderHistoryView = null;
                try {
                    orderHistoryView = new OrderHistoryView(connection, user);

                    contentPanel.removeAll();
                    contentPanel.add(orderHistoryView, BorderLayout.CENTER);
                    contentPanel.revalidate();
                    
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
                System.out.println("Went to Order History");
            }
        });

        products.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Products Page");
            }
        });


    }
    
}
