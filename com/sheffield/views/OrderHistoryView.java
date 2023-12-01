package com.sheffield.views;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
import com.sheffield.model.order.Order.OrderStatus;
import com.sheffield.util.OrderOperations;


public class OrderHistoryView extends JPanel {
    private JTable basketTable;
    private JTable blockedTable;
    private JButton decline;
   
    public OrderHistoryView (Connection connection, User user) throws SQLException {

        JPanel contentPanel = this;
        contentPanel.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());

        contentPanel.add(header, BorderLayout.NORTH);

        JButton backButton = new JButton("Back");
        header.add(backButton,BorderLayout.WEST);

        //Navigates to User Main Page
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
        String[] columnBlockedNames = {"OrderID", "Date","Decline"};

        JComboBox<String> comboBox = new JComboBox<>();
        
            DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
            };

            DefaultTableModel modelBlocked = new DefaultTableModel(columnBlockedNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; 
                }
                };

            basketTable = new JTable(model);
            blockedTable = new JTable(modelBlocked);

            comboBox.addItem("Yes");
            comboBox.addItem("No");

            
            blockedTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboBox));
        

        try {
       
            Order[] userOrders = orderOperations.getAllOrdersByUser(user.getuserId(), connection);
            Arrays.sort(userOrders, Comparator.comparing(Order::getIssueDate).reversed());
            
           //Separate Blocked Orders from Normal
            for (Order order: userOrders){
                if(orderOperations.isBlockedOrder(order, connection) && order.getOrderStatus() != OrderStatus.FULFILLED){
                    Object[] ordersForBlockedTable = {order.getOrderID(), order.getIssueDate(), "test"};
                    modelBlocked.addRow(ordersForBlockedTable);
                }else{
                    Object[] ordersForTable = {order.getOrderID(), order.getIssueDate()};
                    model.addRow(ordersForTable);
                }
            
            }

            
        } catch (SQLException e) {
            e.printStackTrace();
          
        }

        basketTable.setCellSelectionEnabled(false);
        
        basketTable.setColumnSelectionAllowed(false);

        decline = new JButton("Decline Order");
        decline.setEnabled(false);

        //Selection Function
        ListSelectionModel selectionModel = blockedTable.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e){
                if (!e.getValueIsAdjusting()){
                    int selectedRow = blockedTable.getSelectedRow();
                 
                    decline.setEnabled(selectedRow != -1);
                }

            }
        });

        //Action Listnener for Decline Button
        decline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                int selectedRow = blockedTable.getSelectedRow();
                if(selectedRow != -1){
                    int orderId = Integer.parseInt(blockedTable.getValueAt(selectedRow, 0).toString());
                    try {          
                        orderOperations.updateDeclineOrder(orderId, connection);
                        modelBlocked.removeRow(selectedRow);
                    } catch (SQLException e1) {
                      
                        e1.printStackTrace();
                    }
                   
                }
            }
        });

        //Mouse Listener for retrieving Order ID
        basketTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = basketTable.columnAtPoint(e.getPoint());
                int row = basketTable.rowAtPoint(e.getPoint());
          
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
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(decline);

    
        

        panel.add(scrollPane, BorderLayout.NORTH);
        panel.add(scrollPane1, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        


    }
    
}