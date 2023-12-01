package com.sheffield.views;

import javax.swing.*;


import com.sheffield.model.DatabaseConnectionHandler;
import com.sheffield.model.order.Order;
import com.sheffield.model.order.OrderLine;
import com.sheffield.model.order.Order.OrderStatus;
import com.sheffield.model.user.User;
import com.sheffield.util.OrderOperations;
import com.sheffield.util.TestOperations;

import java.sql.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class ProductsView extends JPanel {

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

        JPanel contentPanel = this;
        contentPanel.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        JPanel header = new JPanel(new BorderLayout());

        contentPanel.add(header, BorderLayout.PAGE_START);
        contentPanel.add(panel, BorderLayout.CENTER);

        JButton backButton;
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Products Category");
                ProductsPageView productsPageView = null;
                try {
                    productsPageView = new ProductsPageView(connection, user);
                    // userDetailsView.setVisible(true);
                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(productsPageView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();

                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }

            }
        });

        JButton basketButton = new JButton("My Cart");
        basketButton.addActionListener(new ActionListener() {
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
                        JOptionPane.showMessageDialog(panel, "Your cart is empty");
                    } else {

                        Order newOrder = new Order(orderID, userID, issueDate, total, status);

                        userOrderView = new UserOrderView(connection, newOrder, user);
                        // userDetailsView.setVisible(true);
                        TrainsOfSheffield.getPanel().removeAll();
                        TrainsOfSheffield.getPanel().add(userOrderView, BorderLayout.CENTER);
                        TrainsOfSheffield.getPanel().revalidate();

                    }

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
        header.add(backButton, BorderLayout.WEST);

        header.add(basketButton, BorderLayout.EAST);

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

                    int orderProductID = Integer.valueOf(pIDs.get(final_i));

                    int orderQuantity = Integer.valueOf(String.valueOf(combos[final_i].getSelectedItem()));

                    Float price = prices.get(final_i);

                    Float totalPrice = orderQuantity * price;

                    // temporary orderID
                    int orderID = 6;
                    TestOperations testOperations = new TestOperations();

                    try {
                        String inven = "SELECT Quantity FROM Inventory WHERE ProductID = " + orderProductID;
                        PreparedStatement invenS = connection.prepareStatement(inven);
                        ResultSet invenR = invenS.executeQuery();

                        int itemInven = 0;

                        while (invenR.next()) {
                            itemInven = invenR.getInt(1);
                        }

                        if (orderQuantity > itemInven) {

                            JOptionPane.showMessageDialog(panel,
                                    "Cannot add product. We only have " + itemInven + " stock(s) for this product");
                        } else {
                            ArrayList<Order> orders = testOperations.getAllOrdersObj(connection);
                            Order currentOrder = null;
                            OrderLine currentOrderLine = null;
                            for (int i = 0; i < orders.size(); i++) {
                                if (orders.get(i).getOrderStatus() == OrderStatus.PENDING
                                        && orders.get(i).getUserID().equals(user.getuserId())) {
                                    currentOrder = orders.get(i);
                                }
                            }

                            if (currentOrder == null) {
                                System.out.println("currentOrder is null");

                                Order newOrder = new Order(0, user.getuserId(), new Date(100, 1, 1), 0.00, "pending");
                                testOperations.insertOrder(newOrder, connection);
                                orders = testOperations.getAllOrdersObj(connection);
                                for (int i = 0; i < orders.size(); i++) {
                                    if (orders.get(i).getOrderStatus() == OrderStatus.PENDING
                                            && orders.get(i).getUserID().equals(user.getuserId())) {
                                        currentOrder = orders.get(i);
                                    }
                                }
                                OrderLine orderLine = new OrderLine(0, currentOrder.getOrderID(), orderProductID,
                                        orderQuantity, totalPrice);
                                testOperations.insertOrderLine(orderLine, connection);
                                currentOrder.setTotalCost(currentOrder.getTotalCost() + totalPrice);
                                testOperations.updateOrder(currentOrder, connection);
                            } else {
                                System.out.println("currentOrder is not null");
                                ArrayList<OrderLine> orderLines = testOperations.getOrderLinesFromOrder(currentOrder.getOrderID(), connection);
                                for (int i = 0; i < orderLines.size(); i++) {
                                    if (orderLines.get(i).getProductID() == orderProductID) {
                                        currentOrderLine = orderLines.get(i);
                                    }
                                }
                                OrderLine orderLine;
                                if (currentOrderLine == null) {
                                    orderLine = new OrderLine(0, currentOrder.getOrderID(), orderProductID,
                                            orderQuantity,
                                            totalPrice);
                                    testOperations.insertOrderLine(orderLine, connection);
                                    currentOrder.setTotalCost(currentOrder.getTotalCost() + totalPrice);
                                    testOperations.updateOrder(currentOrder, connection);

                                } else {
                                    orderLine = new OrderLine(currentOrderLine.getOrderLineID(),
                                            currentOrder.getOrderID(),
                                            orderProductID, orderQuantity + currentOrderLine.getQuantity(),
                                            totalPrice + currentOrderLine.getLineCost());
                                    testOperations.updateOrderLine(orderLine, connection);
                                    currentOrder.setTotalCost(currentOrder.getTotalCost() + totalPrice);
                                    testOperations.updateOrder(currentOrder, connection);

                                }

                            }
                        }

                    } catch (SQLException w) {
                        System.out.println("Cannot insert order line");
                    }

                }
            });

            i++;

        }

        JScrollPane sp = new JScrollPane(panel);
        contentPanel.add(sp);

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
