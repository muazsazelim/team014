package com.sheffield.views;

import javax.swing.*;
import javax.swing.text.StyledEditorKit;

import com.sheffield.model.user.User;
import com.sheffield.model.Product;
import com.sheffield.util.TestOperations;

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
        this.getContentPane().setLayout(new BorderLayout());
        this.setSize(720, 600);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        this.add(contentPanel, BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(0,2));

        contentPanel.add(panel, BorderLayout.CENTER);
        

        JLabel titleLabel = new JLabel("Edit Inventory");
        titleLabel.setFont(new Font("Default", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(titleLabel, BorderLayout.NORTH);

        
        JLabel lblPID = new JLabel("Enter Product ID");
        JTextField txtPID = new JTextField();

        JLabel productNameLabel = new JLabel("Enter Product Name");
        JTextField productNameInput = new JTextField();

        JLabel brandNameLabel = new JLabel("Enter Brand Name");
        JTextField brandNameInput = new JTextField();

        JLabel retailPriceLabel = new JLabel("Enter Retail Price");
        JTextField retailPriceInput = new JTextField();

        JLabel gaugeTyepLabel = new JLabel("Enter Gauge Type");
        JTextField gaugeTypeInput = new JTextField();


        panel.add(lblPID);
        panel.add(txtPID);

        panel.add(productNameLabel);
        panel.add(productNameInput);

        panel.add(brandNameLabel);
        panel.add(brandNameInput);

        panel.add(retailPriceLabel);
        panel.add(retailPriceInput);

        panel.add(gaugeTyepLabel);
        panel.add(gaugeTypeInput);

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
                    TestOperations testOperations = new TestOperations();
                    if (!txtPID.getText().equals("")) {
                        int productId = Integer.valueOf(txtPID.getText());

                        Product updatedProduct = testOperations.getProduct(productId, connection);

                        if (!txtQuantity.getText().equals("")) {
                            System.out.println("Getting Stock");

                            int quantity = Integer.valueOf(txtQuantity.getText());
                            System.out.println(quantity);
                            System.out.println(productId);

                            int currentQuantity = testOperations.getStock(productId, connection);
                            
                            System.out.println(currentQuantity);

                            if (currentQuantity >= 0) {
                                System.out.println("Stock Updating");
                                int newQuantity = currentQuantity + quantity;
                                System.out.println(newQuantity);
                                testOperations.updateStock(productId, newQuantity, connection);
                                txtQuantity.setText("");
                            }
                            
                        }

                        if (!productNameInput.getText().equals("")) 
                        {
                            updatedProduct.setProductName(productNameInput.getText());
                            productNameInput.setText("");
                        }   

                        if (!brandNameInput.getText().equals("")) 
                        {
                            updatedProduct.setBrandName(brandNameInput.getText());
                            brandNameInput.setText("");
                        }   

                        if (!retailPriceInput.getText().equals("")) 
                        {
                            float retailPriceFloat = Float.valueOf(retailPriceInput.getText());
                            updatedProduct.setRetailPrie(retailPriceFloat);
                            retailPriceInput.setText("");
                        }   

                        if (!gaugeTypeInput.getText().equals("")) 
                        {
                            updatedProduct.setGaugeType(gaugeTypeInput.getText());
                            gaugeTypeInput.setText("");
                        }   
                        txtPID.setText("");

                        testOperations.updateProduct(updatedProduct, connection);
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


    }

}
