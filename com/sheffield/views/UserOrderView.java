package com.sheffield.views;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import com.sheffield.model.user.User;
import com.sheffield.model.order.Order;
import com.sheffield.model.order.OrderLine;
import com.sheffield.util.OrderOperations;
import com.sheffield.util.TestOperations;

public class UserOrderView extends JPanel {
    private JButton confirmOrder;
    private JButton orderHistory;
    private JButton decline;
    private JTable basketTable;
    
    private TestOperations testOperations = new TestOperations();
    OrderOperations orderOperations = new OrderOperations();

    public UserOrderView(Connection connection, Order order, User user) throws SQLException {

        
        JPanel contentPanel = this;
        contentPanel.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());

        JPanel panel = new JPanel();


        contentPanel.add(header, BorderLayout.NORTH);
        contentPanel.add(panel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");

        header.add(backButton, BorderLayout.WEST);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Products Category");

                //dispose();
                
                ProductsPageView productsPageView = null;
                try {
                    productsPageView = new ProductsPageView(connection, user);
                    //userDetailsView.setVisible(true);
                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(productsPageView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();
                
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
                 
            }
        });

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // panel.setLayout(new GridLayout(0,1));

        JLabel titleLabel = new JLabel("Order Basket");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
        // need to replace with selection from cataloge table
        
        String[] columnNames = { "Order Line ID", "Product ID", "Quantity", "Cost" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // OrderLine[] orderLineForOrder
        try {
            OrderLine[] orderLines = orderOperations.getAllOrdersLinesByOrder(order.getOrderID(), connection);

            for (OrderLine orderline : orderLines) {

                Object[] ordersForTable = { orderline.getOrderLineID(), orderline.getProductID(),
                        orderline.getQuantity(), orderline.getLineCost() };
                model.addRow(ordersForTable);
            }

            basketTable = new JTable(model);
        } catch (SQLException e) {
            e.printStackTrace();

        }

        

        JScrollPane scrollPane = new JScrollPane(basketTable);

        panel.add(scrollPane, BorderLayout.CENTER);

        // Create buttons that links to other pages from default page
        confirmOrder = new JButton("Confirm Order");

        System.out.println(user.getUserType());

        // Add components to the panel
        panel.add(confirmOrder);


        // Create an ActionListener for the view buttons
        confirmOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BankDetailsView bankDetailsView = null;
                try {
                    String userId = order.getUserID();
                    if(testOperations.isUserHaveBankDetails(userId, connection)){
                        orderOperations.updateOrderStatus(order.getOrderID(), "confirmed", connection);
                    }else {
                        // ask josh to link bank details page
                        bankDetailsView = new BankDetailsView(connection, user);
                        bankDetailsView.setVisible(true);

                    }
                    
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });



    }

}