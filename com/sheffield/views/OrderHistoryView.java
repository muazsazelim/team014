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
import com.sheffield.util.OrderOperations;


public class OrderHistoryView extends JFrame {
    private JButton userDetails;
    private JButton orderHistory;
    private JButton products;
    private JTable basketTable;
    private Object[][] data;
    public OrderHistoryView (Connection connection, User user) throws SQLException {

        this.setTitle("Train of Sheffield");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320,320);

 
        JPanel panel = new JPanel();
        this.add(panel);

        this.getContentPane().setLayout(new BorderLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
       // panel.setLayout(new GridLayout(0,1));

        JLabel titleLabel = new JLabel("Order History");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        //sample data -filter for one customer - needs order no. - date of order(cutomer) -filter for all -  customer detail - email - address (staff)
        OrderOperations orderOperations = new OrderOperations();

        try {
            System.out.println(user.getuserId());
            Order[] userOrders = orderOperations.getAllOrdersByUser(user.getuserId(), connection);
            Arrays.sort(userOrders, Comparator.comparing(Order::getIssueDate).reversed());
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

        basketTable.setCellSelectionEnabled(false);
        basketTable.setRowSelectionAllowed(false);
        basketTable.setColumnSelectionAllowed(false);


        basketTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = basketTable.columnAtPoint(e.getPoint());
                int row = basketTable.rowAtPoint(e.getPoint());
                System.out.println(row);
                if (column == 0) { 
                    dispose();
                    OrderDetailsView orderDetailsView = null;
                    try {
                        Order[] userOrders = orderOperations.getAllOrdersByUser(user.getuserId(), connection);
                        Arrays.sort(userOrders, Comparator.comparing(Order::getIssueDate).reversed());
                        Order order = userOrders[row];
                        orderDetailsView = new OrderDetailsView(connection, order);
                        orderDetailsView.setVisible(true);
                        
                         
                    } catch (SQLException i) {
                        i.printStackTrace();
                       
                    }
                }
            }
        });

        
      
        JScrollPane scrollPane = new JScrollPane(basketTable);

        panel.add(scrollPane, BorderLayout.CENTER);

        this.setVisible(true);

        
        // Create buttons that links to other pages from default page
        userDetails = new JButton("Change Details");
        orderHistory = new JButton("Order History");
        products = new JButton("View Products");

        // Add components to the panel
        panel.add(userDetails);
        panel.add(orderHistory);
        panel.add(products);

        this.getContentPane().add(panel, BorderLayout.NORTH);
        this.pack();

        // Create an ActionListener for the view buttons
        userDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("went to Basket Page");
            }
        });
        
        orderHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Order History Page");
            }
        });

        products.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Products Page");
            }
        });

        

    }
    
}