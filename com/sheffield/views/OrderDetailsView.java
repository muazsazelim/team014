package com.sheffield.views;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;

import com.sheffield.model.user.User;
import com.sheffield.model.order.Order;
import com.sheffield.model.order.OrderLine;
import com.sheffield.util.OrderOperations;
import com.sheffield.util.TestOperations;

public class OrderDetailsView extends JPanel {
    private JButton userDetails;
    private JButton orderHistory;
    private JButton products;
    private JTable basketTable;
    
    public OrderDetailsView (Connection connection, Order order, User user, boolean staff) throws SQLException {


        JPanel contentPanel = this;
        contentPanel.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());

        contentPanel.add(header, BorderLayout.NORTH);
 
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        contentPanel.add(panel, BorderLayout.CENTER);

       // panel.setLayout(new GridLayout(0,1));

        JLabel titleLabel = new JLabel("Order Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
       

        OrderOperations orderOperations = new OrderOperations();
        
         try {
            OrderLine[] userOrders = orderOperations.getAllOrdersLinesByOrder(order.getOrderID(), connection);
            Arrays.sort(userOrders, Comparator.comparing(OrderLine::getOrderLineID));
            System.out.println(userOrders.length);
            System.out.println("this works");
            String[] columnNames = {"OrderLineID", "Product Code", "Brand", "Product Name", "Quantity", "Line Cost"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
            };
           
            
            for (OrderLine orderLine: userOrders){
                Object[] ordersForTable = {orderLine.getOrderLineID(), orderOperations.getProductCode(orderLine.getProductID(), connection),
                                            orderOperations.getProductBrand(orderLine.getProductID(), connection),
                                            orderOperations.getProductName(orderLine.getProductID(), connection),
                                            orderLine.getQuantity(), orderLine.getLineCost(), };
                model.addRow(ordersForTable);
            }

             basketTable = new JTable(model);
        } catch (SQLException e) {
            e.printStackTrace();
          
        }

     

        basketTable.setCellSelectionEnabled(false);
        basketTable.setRowSelectionAllowed(false);
        basketTable.setColumnSelectionAllowed(false);

        JScrollPane scrollPane = new JScrollPane(basketTable);

        panel.add(scrollPane, BorderLayout.CENTER);

        JScrollPane scrollPane2 = new JScrollPane(basketTable);
        
        scrollPane2.setBorder(BorderFactory.createTitledBorder("Order Status: " + order.getOrderStatus()));
        panel.add(scrollPane2, BorderLayout.CENTER);
        this.setVisible(true);
        
        JButton backButton = new JButton("Back");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to User Main View");

                //dispose();
                OrderHistoryView orderHistoryView = null;
                OrderManagementStaffView orderManagementStaffView = null;
                try {
                    if (staff) {
                        orderManagementStaffView = new OrderManagementStaffView(connection, user);
                        //userDetailsView.setVisible(true);
                        TrainsOfSheffield.getPanel().removeAll();
                        TrainsOfSheffield.getPanel().add(orderManagementStaffView, BorderLayout.CENTER);
                        TrainsOfSheffield.getPanel().revalidate();
                    } else {
                        orderHistoryView = new OrderHistoryView(connection, user);
                        //userDetailsView.setVisible(true);
                        TrainsOfSheffield.getPanel().removeAll();
                        TrainsOfSheffield.getPanel().add(orderHistoryView, BorderLayout.CENTER);
                        TrainsOfSheffield.getPanel().revalidate();
                    }
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
        });

        header.add(backButton,BorderLayout.WEST);
        

    }

   

    
    
}