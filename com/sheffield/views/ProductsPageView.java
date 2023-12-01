package com.sheffield.views;

import javax.swing.*;

import com.mysql.cj.xdevapi.JsonParser;
import com.sheffield.model.DatabaseConnectionHandler;
import com.sheffield.model.order.Order;
import com.sheffield.model.user.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.*;
import java.sql.Date;

public class ProductsPageView extends JPanel {

    public ProductsPageView(Connection connection, User user) throws SQLException {

        // Create a JPanel to hold the components
        JPanel contentPanel = this;
        contentPanel.setLayout(new BorderLayout());

        JPanel navigation = new JPanel();
        navigation.setLayout(new GridLayout(0, 1));

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());

        contentPanel.add(navigation, BorderLayout.CENTER);
        contentPanel.add(header, BorderLayout.NORTH);

        // Create buttons that links to other pages from default page

        JButton trackPack = new JButton("Track Packs");
        JButton trackPiece = new JButton("Track Pieces");
        JButton locomotives = new JButton("Locomotives");
        JButton trainSet = new JButton("Train Sets");
        JButton rollingStock = new JButton("Rolling Stock");
        JButton controllers = new JButton("Controllers");

        JButton backButton = new JButton("Back");


        navigation.add(trackPack);
        navigation.add(trackPiece);
        navigation.add(locomotives);
        navigation.add(trainSet);
        navigation.add(rollingStock);
        navigation.add(controllers);

        JButton userOrder = new JButton("My Cart");
        navigation.add(userOrder);
        header.add(backButton, BorderLayout.WEST);

        trackPack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Products Page");
                ProductsView productsView = null;

                try {
                    productsView = new ProductsView(connection, 1,user);
                    //userDetailsView.setVisible(true);
                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(productsView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }

            }
        });

        trackPiece.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Products Page");
                ProductsView productsView = null;

                try {
                    productsView = new ProductsView(connection, 2,user);
                    //userDetailsView.setVisible(true);
                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(productsView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }

            }
        });

        locomotives.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Products Page");
                ProductsView productsView = null;

                try {
                    productsView = new ProductsView(connection, 3,user);
                    //userDetailsView.setVisible(true);
                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(productsView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }

            }
        });

        trainSet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Products Page");
                ProductsView productsView = null;

                try {
                    productsView = new ProductsView(connection, 4,user);
                    //userDetailsView.setVisible(true);
                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(productsView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }

            }
        });

        rollingStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Products Page");
                ProductsView productsView = null;

                try {
                    productsView = new ProductsView(connection, 5,user);
                    //userDetailsView.setVisible(true);
                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(productsView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }

            }
        });

        controllers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Products Page");
                ProductsView productsView = null;

                try {
                    productsView = new ProductsView(connection, 6,user);
                    //userDetailsView.setVisible(true);
                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(productsView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }

            }
        });

        userOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Cart Page");
                UserOrderView userOrderView = null;

                // Order userOrder = new Order();

                try {
                    String getQ = "SELECT * FROM Orders WHERE userId = '" + user.getuserId()
                            + "' AND status = 'pending'";

                    PreparedStatement getQS = connection.prepareStatement(getQ);
                    ResultSet getQR = getQS.executeQuery();

                    int orderID = 0;
                    String userID = "";
                    Date issueDate = Date.valueOf("2023-11-11");
                    Double total = 0.00;
                    String status = "pending";

                    while (getQR.next()) {
                        orderID = getQR.getInt("orderID");
                        userID = getQR.getString("userId");
                        // issueDate = getQR.getDate("issueDate");
                        total = getQR.getDouble("totalCost");
                    }

                    if (orderID == 0) {
                        JOptionPane.showMessageDialog(navigation, "Your cart is empty");
                    } else {

                        Order newOrder = new Order(orderID, userID, issueDate, total, status);

                        
                        
                        userOrderView = new UserOrderView(connection, newOrder,user);
                        //userDetailsView.setVisible(true);
                        TrainsOfSheffield.getPanel().removeAll();
                        TrainsOfSheffield.getPanel().add(userOrderView, BorderLayout.CENTER);
                        TrainsOfSheffield.getPanel().revalidate();
       
                    }

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
