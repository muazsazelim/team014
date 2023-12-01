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
import com.sheffield.model.user.User;

public class InventoryView extends JPanel {

    public InventoryView(Connection connection, User user) throws SQLException {

        JPanel contentPanel = this;
        contentPanel.setLayout(new BorderLayout());


        JPanel panel = new JPanel(new BorderLayout());
        JPanel header = new JPanel(new BorderLayout());
        contentPanel.add(header, BorderLayout.NORTH);
        contentPanel.add(panel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Inventory");
        titleLabel.setFont(new Font("Default", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(titleLabel, BorderLayout.PAGE_START);

        JButton backButton;
        backButton = new JButton("Back");

        JButton editPage;
        editPage = new JButton("Add quantity");

        header.add(backButton, BorderLayout.WEST);


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to User Main View");

                
                StaffView staffView = null;
                try {
                    staffView = new StaffView(connection, user);
                    //userDetailsView.setVisible(true);
                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(staffView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
                
            }
        });

        

        editPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Edit Inventory View");

                //dispose();
                EditInventoryView editInventoryView = null;
                try {
                    editInventoryView = new EditInventoryView(connection, user);
                    editInventoryView.setVisible(true);

                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
        });

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        try {
            JTable j;
            String selectAll = "SELECT * FROM Inventory";
            String productName = "SELECT * FROM Product, Inventory WHERE Inventory.ProductID = Product.productID";
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
                String pQuantity = Integer.toString(resultSet2.getInt("Quantity"));
                String pID = Integer.toString(resultSet2.getInt("ProductID"));

                String[] data = { pID, pName, pQuantity };

                tableModel.addRow(data);
            }

            j = new JTable(tableModel) {
                private static final long serialVersionUID = 1L;

                public boolean isCellEditable(int row, int column) {
                    return false;
                };
            };
            JTableHeader tableHeader = j.getTableHeader();
            Font headerFont = new Font("Default", Font.PLAIN, 13);
            tableHeader.setFont(headerFont);

            // j.setBounds(30, 40, 200, 300);
            j.getColumnModel().getColumn(0).setPreferredWidth(5);
            j.getColumnModel().getColumn(1).setPreferredWidth(150);
            j.getColumnModel().getColumn(2).setPreferredWidth(50);

            JScrollPane sp = new JScrollPane(j);
            panel.add(sp);

            panel.add(editPage);
            panel.setSize(320, 280);


        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Re-throw the exception to signal an error.
        }

    }

}