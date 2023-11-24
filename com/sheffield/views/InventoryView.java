package com.sheffield.views;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import javax.swing.table.*;
import java.awt.*;
import com.sheffield.model.DatabaseConnectionHandler;

public class InventoryView extends JFrame {

    public InventoryView(Connection connection) throws SQLException {
        this.setTitle("Train of Sheffield");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320, 320);

        JPanel panel = new JPanel();
        this.add(panel);

        this.getContentPane().setLayout(new BorderLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // panel.setLayout(new GridLayout(0,1));

        JLabel titleLabel = new JLabel("Inventory");
        titleLabel.setFont(new Font("Default", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        panel.add(titleLabel, BorderLayout.NORTH);

        try {
            JTable j;
            String selectAll = "SELECT * FROM Inventory";
            String productName = "SELECT productName FROM Product, Inventory WHERE Inventory.ProductID = Product.productID";
            PreparedStatement preparedStatement = connection.prepareStatement(selectAll);
            PreparedStatement preparedStatement2 = connection.prepareStatement(productName);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet resultSet2 = preparedStatement2.executeQuery();

            String[] columnNames = { "Product Name", "Quantity" };
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

            while (resultSet.next() && resultSet2.next()) {
                String pName = resultSet2.getString("productName");
                String pQuantity = Integer.toString(resultSet.getInt("Quantity"));

                String[] data = { pName, pQuantity };

                tableModel.addRow(data);
            }

            j = new JTable(tableModel);
            JTableHeader tableHeader = j.getTableHeader();
            Font headerFont = new Font("Default", Font.PLAIN, 13);
            tableHeader.setFont(headerFont);

            j.setBounds(30, 40, 200, 300);

            JScrollPane sp = new JScrollPane(j);
            panel.add(sp);
            panel.setSize(320, 320);

            this.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Re-throw the exception to signal an error.
        }

    }

    public static void main(String[] args) {
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        SwingUtilities.invokeLater(() -> {
            InventoryView inven = null;
            try {
                databaseConnectionHandler.openConnection();

                inven = new InventoryView(databaseConnectionHandler.getConnection());
                inven.setVisible(true);

            } catch (Throwable t) {
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }
}