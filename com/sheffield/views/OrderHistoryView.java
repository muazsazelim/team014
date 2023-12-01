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


public class OrderHistoryView extends JPanel {
    private JButton userDetails;
    private JButton orderHistory;
    private JButton products;
    private JTable basketTable;
    private JTable blockedTable;
    private Object[][] data;
    public OrderHistoryView (Connection connection, User user) throws SQLException {

        JPanel contentPanel = this;
        contentPanel.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());

        contentPanel.add(header, BorderLayout.NORTH);

        JButton backButton = new JButton("Back");
        header.add(backButton,BorderLayout.WEST);


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to User Main View");

                //dispose();
                UserMainView userMainView = null;
                try {
                    userMainView = new UserMainView(connection, user);
                    //userDetailsView.setVisible(true);
                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(userMainView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
        });


        JPanel panel = new JPanel();
        contentPanel.add(panel);

        contentPanel.add(panel, BorderLayout.CENTER);



        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
       // panel.setLayout(new GridLayout(0,1));

        JLabel titleLabel = new JLabel("Order History");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        //sample data -filter for one customer - needs order no. - date of order(cutomer) -filter for all -  customer detail - email - address (staff)
        OrderOperations orderOperations = new OrderOperations();
        
        String[] columnNames = {"OrderID", "Date"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
            };

            basketTable = new JTable(model);
            blockedTable = new JTable(model);
        

        try {
            System.out.println(user.getuserId());
            Order[] userOrders = orderOperations.getAllOrdersByUser(user.getuserId(), connection);
            Arrays.sort(userOrders, Comparator.comparing(Order::getIssueDate).reversed());
            
           
            for (Order order: userOrders){
            Object[] ordersForTable = {order.getOrderID(), order.getIssueDate()};
            model.addRow(ordersForTable);
            }

            
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
                    OrderDetailsView orderDetailsView = null;
                    try {
                        Order[] userOrders = orderOperations.getAllOrdersByUser(user.getuserId(), connection);
                        Arrays.sort(userOrders, Comparator.comparing(Order::getIssueDate).reversed());
                        Order order = userOrders[row];
                        orderDetailsView = new OrderDetailsView(connection, order, user, false);
                        TrainsOfSheffield.getPanel().removeAll();
                        TrainsOfSheffield.getPanel().add(orderDetailsView, BorderLayout.CENTER);
                        TrainsOfSheffield.getPanel().revalidate();
                        
                         
                    } catch (SQLException i) {
                        i.printStackTrace();
                       
                    }
                }
            }
        });

        
      
        JScrollPane scrollPane = new JScrollPane(basketTable);
        JScrollPane scrollPane1 = new JScrollPane(blockedTable);
        

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(scrollPane1, BorderLayout.CENTER);

        


    }
    
}