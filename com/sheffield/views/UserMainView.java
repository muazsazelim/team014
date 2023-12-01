package com.sheffield.views;

import javax.swing.*;

import com.sheffield.model.DatabaseConnectionHandler;
import com.sheffield.model.user.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class UserMainView extends JPanel {

    public UserMainView(Connection connection, User user) throws SQLException {

        // Create a JPanel to hold the components
        JPanel contentPanel = this;
        contentPanel.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());

        JButton logOutButton;
        logOutButton = new JButton("Log Out");
        header.add(logOutButton, BorderLayout.WEST);





        JPanel navigation = new JPanel();
        navigation.setLayout(new GridLayout(0, 1));

        contentPanel.add(header, BorderLayout.NORTH);
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

        if (user.getUserType().equals("staff")) {
            staff = new JButton("Staff");
            navigation.add(staff);

            staff.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Went to Staff Page");

                    StaffView staffView = null;
                    try {
                        staffView = new StaffView(connection, user);

                        TrainsOfSheffield.getPanel().removeAll();
                        TrainsOfSheffield.getPanel().add(staffView, BorderLayout.CENTER);
                        TrainsOfSheffield.getPanel().revalidate();

                    } catch (Throwable t) {
                        throw new RuntimeException(t);
                    }
                }
            });

        }
        if (user.getUserType().equals("manager")) {
            staff = new JButton("Staff");
            manager = new JButton("Manager");

            navigation.add(staff);
            navigation.add(manager);

            staff.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Went to Staff Page");

                    StaffView staffView = null;
                    try {
                        staffView = new StaffView(connection, user);

                        TrainsOfSheffield.getPanel().removeAll();
                        TrainsOfSheffield.getPanel().add(staffView, BorderLayout.CENTER);
                        TrainsOfSheffield.getPanel().revalidate();

                    } catch (Throwable t) {
                        throw new RuntimeException(t);
                    }
                }
            });

            manager.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Went to Manager Page");

                    ManagerView managerView = null;
                    try {
                        managerView = new ManagerView(connection, user);

                        TrainsOfSheffield.getPanel().removeAll();
                        TrainsOfSheffield.getPanel().add(managerView, BorderLayout.CENTER);
                        TrainsOfSheffield.getPanel().revalidate();

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

                // dispose();
                UserDetailsView userDetailsView = null;
                try {
                    userDetailsView = new UserDetailsView(connection, user);
                    // userDetailsView.setVisible(true);
                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(userDetailsView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();

                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
        });

        orderHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // dispose();
                OrderHistoryView orderHistoryView = null;
                try {
                    orderHistoryView = new OrderHistoryView(connection, user);

                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(orderHistoryView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();

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
                ProductsPageView productsPageView = null;
                try {
                    productsPageView = new ProductsPageView(connection, user);

                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(productsPageView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();

                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }                

            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("User Logged Out");

                //dispose();
                LoginView loginView = null;
                try {
                    loginView = new LoginView(connection);
                    //userDetailsView.setVisible(true);
                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(loginView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
        });

    }

}
