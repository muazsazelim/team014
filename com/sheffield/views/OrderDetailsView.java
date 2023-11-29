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
    private Object[][] data;
    public OrderDetailsView (Connection connection, Order order) throws SQLException {


        JPanel contentPanel = this;
        contentPanel.setLayout(new BorderLayout());
 
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        contentPanel.add(panel, BorderLayout.CENTER);

       // panel.setLayout(new GridLayout(0,1));

        JLabel titleLabel = new JLabel("Order Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
       

        OrderOperations orderOperations = new OrderOperations();
        TestOperations testOperations = new TestOperations();
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