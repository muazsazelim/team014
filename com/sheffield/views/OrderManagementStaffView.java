package com.sheffield.views;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

import com.sheffield.model.Address;
import com.sheffield.model.order.Order;
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
    private JButton products;
    private JTable basketTable;
    

    public OrderManagementStaffView (Connection connection) throws SQLException {

        this.setTitle("Train of Sheffield");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320,320);

 
        JPanel panel = new JPanel();
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
       // OrderLine[] orderLineForOrder 
        try {
            Order[] userOrders = orderOperations.getAllOrders( connection);

            Order[] confirmedOrders = Arrays.stream(userOrders)
                .filter(order -> order.getOrderStatus() == Order.OrderStatus.CONFIRMED)
                .toArray(Order[]::new);

            Arrays.sort(confirmedOrders, Comparator.comparing(Order::getIssueDate));

            String[] columnNames = {"OrderID", "Date", "Forename", "Surname", "Email", "Delivery Address", "Total Price", "Status", "Valid Bank Details"};
            
            DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return row ==0 && column == statusIndex; 
            }
            };

            basketTable = new JTable(model);
                JComboBox<String> comboBox = new JComboBox<>();
                    if (isBlockedOrder(confirmedOrders[0])){
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
                String addressDisplay = address.getHouseNumber() + ", " + address.getRoadName() + ", " + address.getCityName() + ", " + address.getPostcode();
                Object[] ordersForTable = {order.getOrderID(), order.getIssueDate(), testOperations.getForename(userIDforSearch, connection),
                                        testOperations.getSurname(userIDforSearch, connection),
                                        testOperations.getEmail(userIDforSearch, connection),
                                        addressDisplay, order.getTotalCost(), order.getOrderStatus(), "needs check valid bank details function"};
                
                    

                model.addRow(ordersForTable);

            }
            JScrollPane scrollPane = new JScrollPane(basketTable);
            panel.add(scrollPane, BorderLayout.CENTER);
             

             
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
                if (column == 0) { // Column index
                    showOrderDetailsPopup(row);
                }
            }
        });

        
      
        JScrollPane scrollPane = new JScrollPane(basketTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);

        
        
        archivedOrders = new JButton("Archived Orders");
        orderHistory = new JButton("Order History");
        products = new JButton("View Products");

        
        panel.add(archivedOrders);
        panel.add(orderHistory);
        panel.add(products);

        this.getContentPane().add(panel, BorderLayout.NORTH);
        this.pack();

       
        archivedOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("went to Archived Page");
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
    // orderDetails needs to get orderline  product code & brand & product name & quantity & derived line cost & total cost &staus
    private void showOrderDetailsPopup(int row) {
        JPopupMenu popupMenu = new JPopupMenu();
        String orderDetails = "Order Details:\n " ;
              //  "Order ID: " + data[row][0] + "\n " +
              //  "Product Name: " + data[row][1] + "\n " +
              //  "Quantity: " + data[row][2];
        JMenuItem menuItem = new JMenuItem(orderDetails);
        popupMenu.add(menuItem);
        popupMenu.show(basketTable, basketTable.getCellRect(row, 0, true).x, basketTable.getCellRect(row, 0, true).y);
    }

    private boolean isBlockedOrder(Order order){

        
        return false;
    }

    
}