package com.sheffield.views;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import javax.swing.table.DefaultTableModel;

import com.sheffield.model.Address;
import com.sheffield.model.order.Order;
import com.sheffield.model.order.OrderLine;
import com.sheffield.model.order.Order.OrderStatus;
import com.sheffield.model.user.User;
import com.sheffield.util.OrderOperations;
import com.sheffield.util.TestOperations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collections;
import com.sheffield.model.user.User;


public class OrderManagementStaffView extends JPanel {
    
    private JButton fufill;
    private JTable basketTable;
    private JTable archivedTable;
    private JTable blockedTable;
    private JButton delete;
    private OrderOperations orderOperations = new OrderOperations();
    private TestOperations testOperations = new TestOperations();
    

    public OrderManagementStaffView (Connection connection, User user) throws SQLException {


        JPanel contentPanel = this;
        contentPanel.setLayout(new BorderLayout());
 
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());

        contentPanel.add(header, BorderLayout.NORTH);    
        contentPanel.add(panel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");

        header.add(backButton, BorderLayout.WEST);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to User Main View");

                //dispose();
                StaffView staffView = null;
                try {
                    staffView = new StaffView(connection, user);
                    //userDetailsView.setVisible(true);
                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(staffView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
        });


        //Title Page
        JLabel titleLabel = new JLabel("Order Mangement");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        int statusIndex = 7;

        JComboBox<String> comboBox = new JComboBox<>();


        // Initallising Table: Confirmed - Blocked - Archived
        String[] columnNames = {"OrderID", "Date", "Forename", "Surname", "Email", "Delivery Address", "Total Price", "Status", "Valid Bank Details"};   
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return row ==0 && column == statusIndex; 
            }
        };

        DefaultTableModel modelArchive = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        archivedTable = new JTable(modelArchive);

        String[] columnNameForBlocked = {"OrderID", "Date", "Forename", "Surname", "Email", "Delivery Address", "Total Price", "Status", "Valid Bank Details", "Declined"};
        DefaultTableModel modelBlocked = new DefaultTableModel(columnNameForBlocked, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        blockedTable = new JTable(modelBlocked);
        

        

        try {
            Order[] userOrders = orderOperations.getAllOrders( connection);

            Order[] confirmedOrders = Arrays.stream(userOrders)
                .filter(order -> order.getOrderStatus() == Order.OrderStatus.CONFIRMED)
                .toArray(Order[]::new);
            Arrays.sort(confirmedOrders, Comparator.comparing(Order::getIssueDate));

            basketTable = new JTable(model);
             
            comboBox.addItem("CONFIRMED");
            comboBox.addItem("FULFILL");
                           
            comboBox.setSelectedItem(confirmedOrders[0].getOrderStatus().toString());
            basketTable.getColumnModel().getColumn(statusIndex).setCellEditor(new DefaultCellEditor(comboBox));



            ArrayList<Order> archivedOrders = new ArrayList<>();
            Arrays.sort(userOrders, Comparator.comparing(Order::getIssueDate));

            // Adds data to tables
            for (Order order: userOrders){               
                if (order.getOrderStatus() == OrderStatus.FULFILLED)
                {
                    archivedOrders.add(order);
                    
                }else if (order.getOrderStatus() == OrderStatus.CONFIRMED){

                    if(orderOperations.isBlockedOrder(order, connection)){

                        
                        Object[] tableValues = getTableValues(order, connection);
                        Object[] newTableValue = new Object[tableValues.length + 1];
                        System.arraycopy(tableValues, 0, newTableValue, 0, tableValues.length);

                        if(orderOperations.isDeclinedOrder(order.getOrderID(), connection)){

                            newTableValue[tableValues.length] = "TRUE";
                        }else {
                           newTableValue[tableValues.length] = "FALSE";
                        }
                               
                        modelBlocked.addRow(newTableValue);
                    }else {
                        model.addRow(getTableValues(order, connection)); 
                    }   
                }
                              
            }


            Collections.sort(archivedOrders, Comparator.comparing(Order::getIssueDate));
            for(Order order : archivedOrders){
                modelArchive.addRow(getTableValues(order, connection));
            }

            basketTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int column = basketTable.columnAtPoint(e.getPoint());
                    int row = basketTable.rowAtPoint(e.getPoint());
                    System.out.println(row);
                    if (column == 0) { 
                        
                        OrderDetailsView orderDetailsView = null;
                        try {
                            
                            Order order = confirmedOrders[row];

                            orderDetailsView = new OrderDetailsView(connection, order, user, true);
                            TrainsOfSheffield.getPanel().removeAll();
                            TrainsOfSheffield.getPanel().add(orderDetailsView, BorderLayout.CENTER);
                            TrainsOfSheffield.getPanel().revalidate();
                            
                             
                        } catch (SQLException i) {
                            i.printStackTrace();
                           
                        }
                    }
                }
            });

            
             
        } catch (SQLException e) {
            e.printStackTrace();         
        }


        basketTable.setCellSelectionEnabled(false);
        basketTable.setRowSelectionAllowed(false);
        basketTable.setColumnSelectionAllowed(false);

        archivedTable.setCellSelectionEnabled(false);
        archivedTable.setRowSelectionAllowed(false);
        archivedTable.setColumnSelectionAllowed(false);

        //Setting Dimentions for Tables
        basketTable.setPreferredScrollableViewportSize(new Dimension(100,100));
        blockedTable.setPreferredScrollableViewportSize(new Dimension(100,100));
        archivedTable.setPreferredScrollableViewportSize(new Dimension(100,100));
        
        //Organising Tables on Panes
        JScrollPane scrollPane = new JScrollPane(basketTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Confirmed Orders"));
        panel.add(scrollPane, BorderLayout.NORTH);

        JScrollPane scrollPane3 = new JScrollPane(blockedTable);
        scrollPane3.setBorder(BorderFactory.createTitledBorder("Blocked Orders"));
        panel.add(scrollPane3, BorderLayout.CENTER);
       
        JScrollPane scrollPane2 = new JScrollPane(archivedTable);
        scrollPane2.setBorder(BorderFactory.createTitledBorder("Archived Orders"));
        panel.add(scrollPane2, BorderLayout.SOUTH);

        
       
        // Labelling Buttons
        fufill = new JButton("Fulfill");
        delete = new JButton("Delete");
        delete.setEnabled(false);

        //Creating Selection 
        ListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);     
        blockedTable.setSelectionModel(selectionModel);
        

        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e){
                if (!e.getValueIsAdjusting()){
                    int selectedRow = blockedTable.getSelectedRow();
                    if (selectedRow != -1){
                        
                            
                            if(blockedTable.getValueAt(selectedRow, 9).toString() == "TRUE"){
                            delete.setEnabled(true);
                            }else{delete.setEnabled(false);}
                        
                    }
                    
                    

                    
                    
                }

            }
        });

        
        //Adding Buttons to their own panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(delete);
        buttonPanel.add(fufill);
               
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);


        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                int selectedRow = blockedTable.getSelectedRow();
                
                    int orderId = Integer.parseInt(blockedTable.getValueAt(selectedRow, 0).toString());
                    try {          
                        orderOperations.deleteOrder(orderId, connection);
                        modelBlocked.removeRow(selectedRow);
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                   
                
            }
        });
    
        
     
        fufill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("FULFILL".equals(basketTable.getValueAt(0, statusIndex))){                       
                        int orderId = Integer.valueOf(basketTable.getValueAt(0,0).toString()) ;
                    try {
                        orderOperations.updateOrderStatus(orderId, "fulfilled", connection);
                        model.removeRow(0);

                        Order order = orderOperations.getOrder(orderId, connection);
                        OrderLine[] orderLines = orderOperations.getAllOrdersLinesByOrder(orderId, connection);

                        //updates the quantity in inventory
                        for (OrderLine orderLine : orderLines) {

                            int productId = orderLine.getProductID();
                            String newQ = "SELECT Quantity FROM Inventory WHERE ProductID = " + productId;
                                PreparedStatement nQ = connection.prepareStatement(newQ);
                                ResultSet newQuan = nQ.executeQuery();
                                int currentQuantity = 0;
                                if (newQuan.next()) {
                                    currentQuantity = newQuan.getInt(1);
                                }                 
                            int updatedQuantity =  currentQuantity - orderOperations.getQuantitybyProductID(productId, connection);
                            orderOperations.updateQuantity(productId, updatedQuantity, connection);
                            
                        }
                        modelArchive.addRow(getTableValues(order, connection));
                        System.out.println("chnged order to fulfilled");
                    } catch (SQLException e1) {   
                        e1.printStackTrace();
                    }
                    
                }
                
            }
        });

        

    }
    
    

    

    private Object[] getTableValues(Order order, Connection connection){
        int orderIdForSearch = order.getOrderID();
        String userIDforSearch;
        Object[] ordersForTable = new Object[9];
                try {
                    userIDforSearch = orderOperations.getUserIDbyOrderID(orderIdForSearch, connection);
                    Address address = testOperations.getAddress(userIDforSearch, connection);
                    // EVERYONE NEEDS AN ADDRESS OR THIS DONT WORK
                  //  String addressDisplay = address.getHouseNumber() + ", " + address.getRoadName() + ", " + address.getCityName() + ", " + address.getPostcode();
                     ordersForTable = new Object[]{order.getOrderID(), order.getIssueDate(), testOperations.getForename(userIDforSearch, connection),
                                        testOperations.getSurname(userIDforSearch, connection),
                                        testOperations.getEmail(userIDforSearch, connection),
                                        "addressDisplay", order.getTotalCost(), order.getOrderStatus(), testOperations.isUserHaveBankDetails(userIDforSearch, connection)};
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                

        return ordersForTable;
    }

    
}