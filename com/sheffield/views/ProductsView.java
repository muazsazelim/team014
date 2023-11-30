package com.sheffield.views;

import javax.swing.*;

import com.mysql.cj.result.DoubleValueFactory;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Object;
import com.mysql.cj.xdevapi.PreparableStatement;
import com.sheffield.model.DatabaseConnectionHandler;
import com.sheffield.model.order.OrderLine;
import com.sheffield.model.user.User;
import com.sheffield.util.OrderOperations;

import java.sql.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.Date;

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

    public ProductsView(Connection connection, int n, User user) throws SQLException {
        this.setTitle("Train of Sheffield");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320, 500);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel header = new JPanel(new BorderLayout());
        this.add(header, BorderLayout.PAGE_START);
        this.add(panel, BorderLayout.CENTER);

        JButton mainPage;
        mainPage = new JButton("Back to Main Page");

        JButton inventoryButton = new JButton("Go to Inventory");
        inventoryButton.addActionListener(new ActionListener() {
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

        panel.add(Box.createVerticalStrut(20));

        String productsql = "";
        String productsql1 = "";
        String title = "";

        if (n == 1) {
            productsql = "SELECT * FROM Track_Pack";
            productsql1 = "SELECT * FROM Product WHERE trackPackID IS NOT NULL";
            title = "Track Packs";
        } else if (n == 2) {
            productsql = "SELECT * FROM Track_Piece";
            productsql1 = "SELECT * FROM Product WHERE trackPieceID IS NOT NULL";
            title = "Track Pieces";
        } else if (n == 3) {
            productsql = "SELECT * FROM Locomotive";
            productsql1 = "SELECT * FROM Product WHERE locomotiveID IS NOT NULL";
            title = "Locomotives";
        } else if (n == 4) {
            productsql = "SELECT * FROM Train_Set";
            productsql1 = "SELECT * FROM Product WHERE trainSetID IS NOT NULL";
            title = "Train Sets";
        } else if (n == 5) {
            productsql = "SELECT * FROM Rolling_Stock";
            productsql1 = "SELECT * FROM Product WHERE rollingStockID IS NOT NULL";
            title = "Rolling Stocks";
        } else {
            productsql = "SELECT * FROM Controller";
            productsql1 = "SELECT * FROM Product WHERE controllerID IS NOT NULL";
            title = "Controllers";
        }

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Default", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(titleLabel, BorderLayout.PAGE_START);
        header.add(mainPage, BorderLayout.LINE_END);
        header.add(inventoryButton, BorderLayout.LINE_START);

        PreparedStatement productStatement = connection.prepareStatement(productsql);
        ResultSet products = productStatement.executeQuery();

        PreparedStatement productStatement2 = connection.prepareStatement(productsql1);
        ResultSet products2 = productStatement2.executeQuery();

        String productName;
        String brandName;
        Float retailPrice;
        String gaugeType;
        String productCode;
        int productID;

        JLabel productNameLabel;
        JLabel brandNameLabel;
        JLabel retailLabel;
        JLabel gaugeLabel;
        JLabel productCodeLabel;

        String quan[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

        JComboBox[] combos = new JComboBox[20];

        JButton addOrder = new JButton();

        JLabel selectQuantity;

        int i = 0;

        List<String> pIDs = new ArrayList<>();
        List<Float> prices = new ArrayList<>();

        while (products.next() && products2.next()) {
            final int final_i = i;
            productName = products2.getString("productName");
            brandName = products2.getString("brandName");
            retailPrice = products2.getFloat("retailPrice");
            gaugeType = products2.getString("gaugeType");
            productCode = products2.getString("productCo");
            productID = products2.getInt("productID");

            pIDs.add(Integer.toString(productID));
            prices.add(retailPrice);

            productNameLabel = new JLabel(productName);
            productNameLabel.setFont(new Font("Default", Font.BOLD, 16));
            brandNameLabel = new JLabel("Brand: " + brandName);
            retailLabel = new JLabel("Price: £" + String.valueOf(retailPrice));
            gaugeLabel = new JLabel("Gauge Type: " + gaugeType);
            productCodeLabel = new JLabel("Product Code: " + productCode);

            panel.add(productNameLabel);
            panel.add(Box.createVerticalStrut(10));
            panel.add(brandNameLabel);
            panel.add(retailLabel);
            panel.add(gaugeLabel);
            panel.add(productCodeLabel);

            panel.add(Box.createVerticalStrut(10));

            combos[i] = new JComboBox<>(quan);
            selectQuantity = new JLabel("Please enter quantity: ");

            addOrder = new JButton("Add to Order");

            panel.add(selectQuantity);

            panel.add(Box.createVerticalStrut(10));
            panel.add(combos[i]);

            panel.add(addOrder);

            panel.add(Box.createVerticalStrut(30));

            addOrder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String orderProductID = pIDs.get(final_i);

                    int orderQuantity = Integer.valueOf(String.valueOf(combos[final_i].getSelectedItem()));

                    Float price = prices.get(final_i);

                    Float totalPrice = orderQuantity * price;

                    // temporary orderID
                    int orderID = 6;

                    try {
                        String orderLineQuery = "SELECT COUNT(*) FROM Order_Line";
                        String orderQ = "SELECT * FROM Orders WHERE userId = '" + user.getuserId()
                                + "' AND status = 'pending'";

                        PreparedStatement orderLineStatement = connection.prepareStatement(orderLineQuery);
                        ResultSet orderLineResultSet = orderLineStatement.executeQuery();

                        PreparedStatement orderLineStatement2 = connection.prepareStatement(orderQ);
                        ResultSet orderLineResultSet2 = orderLineStatement2.executeQuery();

                        int currOrderID = 0;
                        while (orderLineResultSet2.next()) {
                            currOrderID = orderLineResultSet2.getInt("orderID");
                        }

                        if (currOrderID == 0) {
                            String addOrderQ = "INSERT INTO Orders (userId, issueDate, totalCost, status) VALUES (?, CURRENT_TIMESTAMP,?, ?)";
                            PreparedStatement addO = connection.prepareStatement(addOrderQ);
                            addO.setString(1, user.getuserId());
                            // addO.setDate(2, (java.sql.Date) d);
                            addO.setDouble(2, 0.00);
                            addO.setString(3, "pending");

                            addO.executeUpdate();

                            String newOI = "SELECT COUNT(*) FROM Orders";
                            PreparedStatement newOIS = connection.prepareStatement(newOI);
                            ResultSet newOIII = newOIS.executeQuery();
                            int newOrderID1 = 0;
                            while (newOIII.next()) {
                                newOrderID1 = newOIII.getInt(1);
                            }

                            orderID = newOrderID1 + 1;

                        } else {
                            orderID = currOrderID;
                        }

                        int maxOrderLine = 0;
                        while (orderLineResultSet.next()) {
                            maxOrderLine = orderLineResultSet.getInt(1);
                        }

                        OrderLine orderLine = new OrderLine(maxOrderLine + 1, orderID, Integer.parseInt(orderProductID),
                                orderQuantity, totalPrice);
                        OrderOperations orderOperations = new OrderOperations();

                        orderOperations.addOrderLine(orderLine, connection);
                        JOptionPane.showMessageDialog(panel, "Item(s) added to order");

                        String orderTotalS = "SELECT * FROM Orders WHERE orderID = " + orderID;
                        PreparedStatement orderT = connection.prepareStatement(orderTotalS);
                        ResultSet orderTR = orderT.executeQuery();

                        Double currTotal = 0.00;
                        while (orderTR.next()) {
                            currTotal = orderTR.getDouble("totalCost");
                        }

                        Double totalForOrder = Double.sum(currTotal, totalPrice);

                        String updateOrder = "UPDATE Orders SET totalCost = ? WHERE orderID = ?";
                        PreparedStatement updateO = connection.prepareStatement(updateOrder);
                        updateO.setDouble(1, totalForOrder);
                        updateO.setInt(2, orderID);
                        updateO.executeUpdate();

                    } catch (SQLException w) {
                        System.out.println("Cannot insert order line");
                    }

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

                User user1 = new User("a496d669-8275-4e66-9c46-37f5d828ee34", "jurihan@test.com",
                        "3d1ae7ee74752fc7b3808ea93e69bf35e73d7ad8bd759bd53e2204076a87ed7a", 0,
                        false, "Juri", "Han", "customer");

                pv = new ProductsView(databaseConnectionHandler.getConnection(), 3, user1);
                pv.setVisible(true);

            } catch (Throwable t) {
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }
}
