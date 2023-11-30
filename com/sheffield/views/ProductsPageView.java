package com.sheffield.views;

import javax.swing.*;

import com.sheffield.model.DatabaseConnectionHandler;
import com.sheffield.model.user.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class ProductsPageView extends JPanel {

    public ProductsPageView(Connection connection, User user) throws SQLException {

        // Create a JPanel to hold the components
        JPanel contentPanel = this;
        contentPanel.setLayout(new BorderLayout());

        JPanel navigation = new JPanel();
        navigation.setLayout(new GridLayout(0, 1));

        contentPanel.add(navigation, BorderLayout.CENTER);

        // Create buttons that links to other pages from default page


        JButton locomotives;



        locomotives = new JButton("Locomotives");


        navigation.add(locomotives);

        locomotives.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Products Page");
                ProductsView productsView = null;

                try {
                    productsView = new ProductsView(connection, 3,user);

                    productsView.setVisible(true);

                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }                

            }
        });

    }

}
