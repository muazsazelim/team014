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

import com.sheffield.model.user.User;
import com.sheffield.model.order.Order;
import com.sheffield.util.OrderOperations;






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

        JLabel titleLabel = new JLabel("Basket");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
        // need to replace with selection from cataloge table
        OrderOperations orderOperations = new OrderOperations();
       // OrderLine[] orderLineForOrder 
        try {
            Order[] userOrders = orderOperations.getAllOrdersByUser(user.getuserId(), connection);

            String[] columnNames = {"OrderID", "Date"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
            };
           
            for (Order order: userOrders){
            Object[] ordersForTable = {order.getOrderID(), order.getIssueDate()};
            model.addRow(ordersForTable);
            }

             basketTable = new JTable(model);
        } catch (SQLException e) {
            e.printStackTrace();
          
        }

        basketTable.getDefaultEditor(Object.class).addCellEditorListener(new CellEditorListener() {

            @Override
            public void editingStopped(ChangeEvent e) {
                int row = basketTable.getSelectedRow();
                int column = basketTable.getSelectedColumn();
                Object editedValue = basketTable.getValueAt(row, column);

                System.out.println("Cell edited: Row=" + row + ", Column=" + column + ", New Value=" + editedValue);

                data[row][column] = editedValue;
                
            }

            @Override
            public void editingCanceled(ChangeEvent e) {
                int row = basketTable.getSelectedRow();
                int column = basketTable.getSelectedColumn();
               
                Object originalValue = data[row][column];
                basketTable.setValueAt(originalValue, row, column);
            }


        });
  
      
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
                //creates new order for customer
                // needs the array of orderlines from table and adds them to the table
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
                System.out.println("opening order");
            }
        });

      

        

    }

}