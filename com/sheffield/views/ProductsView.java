package com.sheffield.views;

import javax.swing.*;

import com.sheffield.model.DatabaseConnectionHandler;

import java.sql.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProductsView extends JFrame {

    /*
     * productType
     * 1 - track pack
     * 2 - track piece
     * 3 - locomotive
     * 4 - train set
     * 5 - rolling stock
     * 6 - controller
     */

    public ProductsView(Connection connection, int n) throws SQLException {
        this.setTitle("Train of Sheffield");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320, 500);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel header = new JPanel(new BorderLayout());
        this.add(header, BorderLayout.PAGE_START);
        this.add(panel, BorderLayout.CENTER);

        JButton mainPage;
        mainPage = new JButton("Back to Main Page");

        panel.add(Box.createVerticalStrut(20));

        String productsql = "";
        String productsql1 = "";
        String title = "";

        if (n == 1) {
            productsql = "SELECT * FROM Track_Pack";
            productsql1 = "SELECT * FROM Product WHERE trackPackID IS NOT NULL";
            title = "Track Pieces";
        } else if (n == 2) {
            productsql = "SELECT * FROM Track_Piece";
            productsql1 = "SELECT * FROM Product WHERE trackPieceID IS NOT NULL";
            title = "Track Pieces";
        } else if (n == 3) {
            productsql = "SELECT * FROM Locomotive";
            productsql1 = "SELECT * FROM Product WHERE locomotiveID IS NOT NULL";
            title = "Track Pieces";
        } else if (n == 4) {
            productsql = "SELECT * FROM Train_Set";
            productsql1 = "SELECT * FROM Product WHERE trainSetID IS NOT NULL";
            title = "Track Pieces";
        } else if (n == 5) {
            productsql = "SELECT * FROM Rolling_Stock";
            productsql1 = "SELECT * FROM Product WHERE rollingStockID IS NOT NULL";
            title = "Track Pieces";
        } else {
            productsql = "SELECT * FROM Controller";
            productsql1 = "SELECT * FROM Product WHERE controllerID IS NOT NULL";
            title = "Track Pieces";
        }

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Default", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(titleLabel, BorderLayout.PAGE_START);
        header.add(mainPage, BorderLayout.LINE_END);

        PreparedStatement productStatement = connection.prepareStatement(productsql);
        ResultSet products = productStatement.executeQuery();

        PreparedStatement productStatement2 = connection.prepareStatement(productsql1);
        ResultSet products2 = productStatement2.executeQuery();

        String productName;
        String brandName;
        Float retailPrice;
        String gaugeType;
        String productCode;

        JLabel productNameLabel;
        JLabel brandNameLabel;
        JLabel retailLabel;
        JLabel gaugeLabel;
        JLabel productCodeLabel;

        String quan[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

        JComboBox[] combos = new JComboBox[20];

        JButton addOrder = new JButton();

        int i = 0;

        while (products.next() && products2.next()) {
            productName = products2.getString("productName");
            brandName = products2.getString("brandName");
            retailPrice = products2.getFloat("retailPrice");
            gaugeType = products2.getString("gaugeType");
            productCode = products2.getString("productCo");

            productNameLabel = new JLabel(productName);
            productNameLabel.setFont(new Font("Default", Font.BOLD, 16));
            brandNameLabel = new JLabel("Brand: " + brandName);
            retailLabel = new JLabel("Price: £" + String.valueOf(retailPrice));
            gaugeLabel = new JLabel("Gauge Type: " + gaugeType);
            productCodeLabel = new JLabel("Product Code: " + productCode);

            panel.add(productNameLabel);
            panel.add(brandNameLabel);
            panel.add(retailLabel);
            panel.add(gaugeLabel);
            panel.add(productCodeLabel);

            combos[i] = new JComboBox<>(quan);

            addOrder = new JButton("Add to Order");

            panel.add(combos[i]);

            panel.add(addOrder);

            panel.add(Box.createVerticalStrut(10));

            final int final_i = i;

            addOrder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Button for item number " + final_i);
                    System.out.println("Quantity " + combos[final_i].getSelectedItem());

                }
            });

            i++;

        }

        JScrollPane sp = new JScrollPane(panel);
        this.add(sp);

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

    }

    public static void main(String[] args) {
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        SwingUtilities.invokeLater(() -> {
            ProductsView pv = null;
            try {
                databaseConnectionHandler.openConnection();

                pv = new ProductsView(databaseConnectionHandler.getConnection(), 3);
                pv.setVisible(true);

            } catch (Throwable t) {
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }
}
