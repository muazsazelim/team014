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

public class OrderDetailsView extends JFrame {
    private JButton userDetails;
    private JButton orderHistory;
    private JButton products;
    private JTable basketTable;
    private Object[][] data;
    public OrderDetailsView (Connection connection, Order order) throws SQLException {

        this.setTitle("Train of Sheffield");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320,320);

 
        JPanel panel = new JPanel();
        this.add(panel);

        this.getContentPane().setLayout(new BorderLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
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
                                            orderLine.getQuantity(), orderLine.getLineCost()};
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
                if (column == 0) { // Column index
                    showOrderDetailsPopup(row);
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
    // orderDetails needs to get orderline  product code & brand & product name & quantity & derived line cost & total cost &staus
    private void showOrderDetailsPopup(int row) {
        JPopupMenu popupMenu = new JPopupMenu();
        String orderDetails = "Order Details:\n " +
                "Order ID: " + data[row][0] + "\n " +
                "Product Name: " + data[row][1] + "\n " +
                "Quantity: " + data[row][2];
        JMenuItem menuItem = new JMenuItem(orderDetails);
        popupMenu.add(menuItem);
        popupMenu.show(basketTable, basketTable.getCellRect(row, 0, true).x, basketTable.getCellRect(row, 0, true).y);
    }

    
    
}