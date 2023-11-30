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

public class UserOrderView extends JFrame {
    private JButton confirmOrder;
    private JButton orderHistory;
    private JTable basketTable;
    private Object[][] data;
    

    public UserOrderView (Connection connection, User user) throws SQLException {

        this.setTitle("Train of Sheffield");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320,320);

 
        JPanel panel = new JPanel();
        this.add(panel);

        this.getContentPane().setLayout(new BorderLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
       // panel.setLayout(new GridLayout(0,1));

        JLabel titleLabel = new JLabel("Order Basket");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        // need to replace with selection from cataloge table
        OrderOperations orderOperations = new OrderOperations();
        TestOperations testOperations = new TestOperations();

        String[] columnNames = {"Order Line ID", "Product ID", "Quantity", "Cost"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
            };

        try {
            Order pendingOrder = orderOperations.getPendingOrderByUserID(user.getuserId(), connection);
            OrderLine[] orderLines = orderOperations.getAllOrdersLinesByOrder(pendingOrder.getOrderID(), connection);
         
            for (OrderLine orderline: orderLines){         
                Object[] ordersForTable = {orderline.getOrderLineID(), orderline.getProductID(), orderline.getQuantity(), orderline.getLineCost()};
                model.addRow(ordersForTable);
            }

            basketTable = new JTable(model);
        } catch (SQLException e) {
            e.printStackTrace();
          
        }

    
  
      
        JScrollPane scrollPane = new JScrollPane(basketTable);

        panel.add(scrollPane, BorderLayout.CENTER);

        this.setVisible(true);

        
        // Create buttons that links to other pages from default page
        confirmOrder = new JButton("Confirm Order");
        orderHistory = new JButton("Order History");
        

        // Add components to the panel
        panel.add(confirmOrder);
        panel.add(orderHistory);
   
        

        this.getContentPane().add(panel, BorderLayout.NORTH);
        this.pack();

        // Create an ActionListener for the view buttons
        confirmOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                BankDetailsView bankDetailsView = null;
                UserMainView userMainView = null;
                try {
                    boolean isValidBankDetails = testOperations.isUserHaveBankDetails(user.getuserId(), connection);

                    if(isValidBankDetails){
                        Order pendingOrder = orderOperations.getPendingOrderByUserID(user.getuserId(), connection);
                        orderOperations.updateOrderStatus(pendingOrder.getOrderID(), "confirmed", connection);                  
                        model.setRowCount(0);
                        userMainView = new UserMainView(connection, user);
                        userMainView.setVisible(true);
                    }else{
                        bankDetailsView = new BankDetailsView(connection);
                        bankDetailsView.setVisible(true);
                    }
                    
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
            }
        });

        
        
        orderHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                OrderHistoryView orderHistoryView = null;
                try {
                    orderHistoryView = new OrderHistoryView(connection, user);
                    orderHistoryView.setVisible(true);
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
                System.out.println("opening order history");
            }
        });

      

        

    }

}