package com.sheffield.views;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.sheffield.model.DatabaseConnectionHandler;

public class InventoryView extends JFrame {

    public InventoryView(Connection connection) throws SQLException {
        this.setTitle("Train of Sheffield");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320, 320);

        JPanel panel = new JPanel(new BorderLayout());
        this.add(panel);

        JLabel titleLabel = new JLabel("Inventory");
        titleLabel.setFont(new Font("Default", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JButton managerPage;
        managerPage = new JButton("Manager Page");
        JButton staffPage;
        staffPage = new JButton("Staff Page");
        JButton editPage;
        editPage = new JButton("Edit Inventory");

        panel.add(managerPage);
        panel.add(staffPage);

        managerPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Manager View");

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

        staffPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Staff View");

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

        editPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Edit Inventory View");

                dispose();
                EditInventoryView editInventoryView = null;
                try {
                    editInventoryView = new EditInventoryView(connection);
                    editInventoryView.setVisible(true);

                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
        });

        this.getContentPane().setLayout(new BorderLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        try {
            JTable j;
            String selectAll = "SELECT * FROM Inventory";
            String productName = "SELECT productName FROM Product, Inventory WHERE Inventory.ProductID = Product.productID";
            String productID = "SELECT ProductID FROM Inventory";
            PreparedStatement preparedStatement = connection.prepareStatement(selectAll);
            PreparedStatement preparedStatement2 = connection.prepareStatement(productName);
            PreparedStatement preparedStatement3 = connection.prepareStatement(productID);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            ResultSet resultSet3 = preparedStatement3.executeQuery();

            String[] columnNames = { "ID", "Product Name", "Quantity" };
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

            while (resultSet.next() && resultSet2.next() && resultSet3.next()) {
                String pName = resultSet2.getString("productName");
                String pQuantity = Integer.toString(resultSet.getInt("Quantity"));
                String pID = Integer.toString(resultSet3.getInt("ProductID"));

                String[] data = { pID, pName, pQuantity };

                tableModel.addRow(data);
            }

            j = new JTable(tableModel);
            JTableHeader tableHeader = j.getTableHeader();
            Font headerFont = new Font("Default", Font.PLAIN, 13);
            tableHeader.setFont(headerFont);

            // j.setBounds(30, 40, 200, 300);
            j.getColumnModel().getColumn(0).setPreferredWidth(5);
            j.getColumnModel().getColumn(2).setPreferredWidth(5);

            JScrollPane sp = new JScrollPane(j);
            panel.add(sp);

            panel.add(editPage);
            panel.setSize(320, 280);

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