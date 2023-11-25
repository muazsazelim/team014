package com.sheffield.views;

import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditInventoryView extends JFrame {
    public EditInventoryView(Connection connection) throws SQLException {
        this.setTitle("Train of Sheffield");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320, 320);

        JPanel panel = new JPanel(new BorderLayout());
        this.add(panel);

        JLabel titleLabel = new JLabel("Edit Inventory");
        titleLabel.setFont(new Font("Default", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextField txtPID;
        JLabel lblPID;

        lblPID = new JLabel("Enter Product ID");
        txtPID = new JTextField();
        txtPID.setToolTipText("Product ID");
        txtPID.selectAll();

        panel.add(lblPID);
        panel.add(txtPID);

        JTextField txtQuantity;
        JLabel lblQuantity;

        lblQuantity = new JLabel("Enter Quantity");
        txtQuantity = new JTextField();
        txtQuantity.setToolTipText("Product Quantity");
        txtQuantity.selectAll();

        panel.add(lblQuantity);
        panel.add(txtQuantity);

        JButton saveData;
        saveData = new JButton("Save Data");

        saveData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productID;
                String quantity;
                productID = txtPID.getText().strip();
                quantity = txtQuantity.getText().strip();
                try {
                    if (!productID.equals("") && !quantity.equals("")) {
                        try {
                            int pIDInt = Integer.parseInt(productID);
                            Integer.parseInt(quantity);
                            if (pIDInt > 0 && pIDInt < 14) {
                                Statement stmt = connection.createStatement();
                                String query = "UPDATE Inventory SET Quantity = " + quantity + " WHERE ProductID = "
                                        + productID;
                                stmt.executeUpdate(query);

                                txtPID.setText("");
                                txtQuantity.setText("");

                                JOptionPane.showMessageDialog(panel, "Inventory Updated");
                            } else {

                                JOptionPane.showMessageDialog(panel, "Product did not exists");
                            }
                        } catch (NumberFormatException n) {

                            JOptionPane.showMessageDialog(panel, "Please Enter Only Number");

                        }
                    } else {

                        JOptionPane.showMessageDialog(panel, "Empty Input");
                    }
                } catch (Exception s) {
                    s.printStackTrace();

                }

            }
        });

        panel.add(saveData);

        JButton back;
        back = new JButton("Back to Inventory");
        panel.add(back);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Inventory View");

                dispose();
                InventoryView inventoryView = null;
                try {
                    inventoryView = new InventoryView(connection);
                    inventoryView.setVisible(true);

                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
        });

        this.getContentPane().setLayout(new BorderLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.setSize(320, 170);
        this.setVisible(true);
    }

}