package com.sheffield.views;

import javax.swing.*;

import com.sheffield.model.user.User;

import java.sql.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditInventoryView extends JFrame {
    public EditInventoryView(Connection connection, User user) throws SQLException {
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

        lblQuantity = new JLabel("Enter Amount to Add");
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
                try {
                    String productID;
                    String quantity;
                    String totalProductID = "SELECT * From Product";
                    PreparedStatement tPID = connection.prepareStatement(totalProductID);
                    ResultSet tProductID = tPID.executeQuery();
                    List<String> pIDs = new ArrayList<>();
                    while (tProductID.next()) {
                        pIDs.add(Integer.toString(tProductID.getInt("productID")));
                    }
                    productID = txtPID.getText().strip();
                    quantity = txtQuantity.getText().strip();
                    try {
                        if (!productID.equals("") && !quantity.equals("")) {
                            try {
                                Integer.parseInt(productID);

                                Integer.parseInt(quantity);
                                if (pIDs.contains(productID)) {

                                    String newQ = "SELECT Quantity FROM Inventory WHERE ProductID = " + productID;
                                    PreparedStatement nQ = connection.prepareStatement(newQ);
                                    ResultSet newQuan = nQ.executeQuery();
                                    int currentQuantity = 0;
                                    int totalQuantity = 0;
                                    while (newQuan.next()) {
                                        currentQuantity = newQuan.getInt(1);
                                    }

                                    int newQuantity = Integer.parseInt(quantity);
                                    totalQuantity = currentQuantity + newQuantity;

                                    Statement stmt = connection.createStatement();
                                    String query = "UPDATE Inventory SET Quantity = " + totalQuantity
                                            + " WHERE ProductID = "
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
                } catch (SQLException w) {
                    w.printStackTrace();
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
                    inventoryView = new InventoryView(connection, user);
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
