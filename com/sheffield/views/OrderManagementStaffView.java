package com.sheffield.views;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

import com.sheffield.model.Address;
import com.sheffield.model.order.Order;
import com.sheffield.model.order.OrderLine;
import com.sheffield.util.OrderOperations;
import com.sheffield.util.TestOperations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;

public class OrderManagementStaffView extends JFrame {
    private JButton archivedOrders;
    private JButton orderHistory;
    private JButton fufill;
    private JTable basketTable;
    private JTable archivedTable;
    private JButton delete;
    

    public OrderManagementStaffView (Connection connection) throws SQLException {

        this.setTitle("Train of Sheffield");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320,320);

 
        JPanel panel = new JPanel();
        JPanel container = new JPanel();
        this.add(panel);

        this.getContentPane().setLayout(new BorderLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
       // panel.setLayout(new GridLayout(0,1));

        JLabel titleLabel = new JLabel("Order Mangement");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        int statusIndex = 7;

        OrderOperations orderOperations = new OrderOperations();
        TestOperations testOperations = new TestOperations();

        JComboBox<String> comboBox = new JComboBox<>();

        String[] columnNames = {"OrderID", "Date", "Forename", "Surname", "Email", "Delivery Address", "Total Price", "Status", "Valid Bank Details"};
            
            DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return row ==0 && column == statusIndex; 
            }
            };
        
       // OrderLine[] orderLineForOrder 
        try {
            Order[] userOrders = orderOperations.getAllOrders( connection);

            Order[] confirmedOrders = Arrays.stream(userOrders)
                .filter(order -> order.getOrderStatus() == Order.OrderStatus.CONFIRMED)
                .toArray(Order[]::new);

            Arrays.sort(confirmedOrders, Comparator.comparing(Order::getIssueDate));

            

            basketTable = new JTable(model);
                
                    if (isBlockedOrder(confirmedOrders[0], connection)){
                        comboBox.addItem("CONFIRMED");
                        comboBox.addItem("DELETE");
                    
                    }else {
                        comboBox.addItem("CONFIRMED");
                        comboBox.addItem("FULFILL");
                    }
                
                comboBox.setSelectedItem(confirmedOrders[0].getOrderStatus().toString());
                basketTable.getColumnModel().getColumn(statusIndex).setCellEditor(new DefaultCellEditor(comboBox));
           
            for (Order order: confirmedOrders){
                int orderIdForSearch = order.getOrderID();
                String userIDforSearch = orderOperations.getUserIDbyOrderID(orderIdForSearch, connection);
                Address address = testOperations.getAddress(userIDforSearch, connection);
               // String addressDisplay = address.getHouseNumber() + ", " + address.getRoadName() + ", " + address.getCityName() + ", " + address.getPostcode();
                Object[] ordersForTable = {order.getOrderID(), order.getIssueDate(), testOperations.getForename(userIDforSearch, connection),
                                        testOperations.getSurname(userIDforSearch, connection),
                                        testOperations.getEmail(userIDforSearch, connection),
                                        "addressDisplay", order.getTotalCost(), order.getOrderStatus(), testOperations.isUserHaveBankDetails(userIDforSearch, connection)};
                
                    

                model.addRow(ordersForTable);

            }

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
                            
                            Order order = confirmedOrders[row];
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

            DefaultTableModel modelArchive = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            };

            archivedTable = new JTable(modelArchive);
             

             
        } catch (SQLException e) {
            e.printStackTrace();         
        }


        basketTable.setCellSelectionEnabled(false);
        basketTable.setRowSelectionAllowed(false);
        basketTable.setColumnSelectionAllowed(false);
    
      
        JScrollPane scrollPane = new JScrollPane(basketTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Confirmed Orders"));
        panel.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);

        
        JScrollPane scrollPane2 = new JScrollPane(archivedTable);
        scrollPane2.setBorder(BorderFactory.createTitledBorder("Archived Orders"));
        panel.add(scrollPane2, BorderLayout.CENTER);
        this.setVisible(true);
       
        
        orderHistory = new JButton("Order History");
        fufill = new JButton("View Products");
        delete = new JButton("Delete");

        
        panel.add(delete);
        panel.add(orderHistory);
        panel.add(fufill);

        this.getContentPane().add(panel, BorderLayout.NORTH);
        this.pack();


       
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("DELETE".equals(basketTable.getValueAt(0, statusIndex))){
                    int orderId = Integer.valueOf(basketTable.getValueAt(0,0).toString()) ;
                    try {
                        orderOperations.deleteOrder(orderId, connection);
                    } catch (SQLException e1) {
                       
                        e1.printStackTrace();
                    }
                    model.removeRow(0);
                    System.out.println("deleted order");
                        

                }
            }
        });
        
        orderHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Order History Page");
            }
        });

        fufill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("FULFILL".equals(basketTable.getValueAt(0, statusIndex))){                       
                        
                }
                
            }
        });

        

    }
    

    private boolean isBlockedOrder(Order order, Connection connection) throws SQLException{
        System.out.println("blocked funciton");
        OrderOperations orderOperations = new OrderOperations();
        try{
            for (OrderLine orderLine : orderOperations.getAllOrdersLinesByOrder(order.getOrderID(), connection)) {
                int stockQuantity = orderOperations.getQuantitybyProductID(orderLine.getProductID(), connection);
                if (stockQuantity - orderLine.getQuantity()< 0){
                    System.out.println(stockQuantity - orderLine.getQuantity());
                    return true;
                }
            }
            return false;
            
        }catch (SQLException e){
            e.printStackTrace();
            throw e;
        }
        
        
    }

    
}