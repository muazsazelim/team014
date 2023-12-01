package com.sheffield.views;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import com.sheffield.model.user.User;
import com.sheffield.model.order.Order;
import com.sheffield.model.order.OrderLine;
import com.sheffield.util.OrderOperations;
import com.sheffield.util.TestOperations;

public class UserOrderView extends JPanel {
    private JButton confirmOrder;
    private JButton delete;
    private JButton orderHistory;
    private JButton decline;
    private JTable basketTable;
    
    private TestOperations testOperations = new TestOperations();
    private OrderOperations orderOperations = new OrderOperations();

    public UserOrderView(Connection connection, Order order, User user) throws SQLException {

        
        JPanel contentPanel = this;
        contentPanel.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());

        JPanel panel = new JPanel();


        contentPanel.add(header, BorderLayout.NORTH);
        contentPanel.add(panel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");

        header.add(backButton, BorderLayout.WEST);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Products Category");

                //dispose();
                
                ProductsPageView productsPageView = null;
                try {
                    productsPageView = new ProductsPageView(connection, user);
                    //userDetailsView.setVisible(true);
                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(productsPageView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();
                
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
                 
            }
        });

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // panel.setLayout(new GridLayout(0,1));

        JLabel titleLabel = new JLabel("Order Basket");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
        // need to replace with selection from cataloge table
        
        String[] columnNames = { "Order Line ID", "Product ID", "Quantity", "Cost" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };
        // OrderLine[] orderLineForOrder
        try {
            OrderLine[] orderLines = orderOperations.getAllOrdersLinesByOrder(order.getOrderID(), connection);

            for (OrderLine orderline : orderLines) {

                Object[] ordersForTable = { orderline.getOrderLineID(), orderline.getProductID(),
                        orderline.getQuantity(), orderline.getLineCost() };
                model.addRow(ordersForTable);
            }

            basketTable = new JTable(model);
        } catch (SQLException e) {
            e.printStackTrace();

        }

        basketTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int column = basketTable.columnAtPoint(e.getPoint());
                int row = basketTable.rowAtPoint(e.getPoint());
                if(column == 2){
                    int orderLineId = Integer.parseInt(model.getValueAt(row, 0).toString());
                    int productId = Integer.parseInt(model.getValueAt(row, 1).toString());
                    int newQuantity = Integer.parseInt(model.getValueAt(row, 2).toString());
                    try {
                        orderOperations.updateOrderLineQuantity(orderLineId, newQuantity, connection);
                        double newCost = orderOperations.getProductCost(productId, connection) * newQuantity;
                        orderOperations.updateOrderLineCost(newCost, orderLineId, connection);
                        model.setValueAt(newCost, row, 3);

                    
                        
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    
                }
            }
        });

        

        JScrollPane scrollPane = new JScrollPane(basketTable);

        panel.add(scrollPane, BorderLayout.CENTER);

        // Create buttons that links to other pages from default page
        confirmOrder = new JButton("Confirm Order");
        delete = new JButton("Delete");
        delete.setEnabled(false);

        ListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);     
        basketTable.setSelectionModel(selectionModel);
        
         selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e){
                if (!e.getValueIsAdjusting()){
                    int selectedRow = basketTable.getSelectedRow();
                    if (selectedRow != -1){
                                                   
                        delete.setEnabled(true);
                                                
                    }else {
                         delete.setEnabled(false);
                    }                   
                    
                }

            }
        });


        System.out.println(user.getUserType());

        JLabel totalCost = new JLabel();
        String totalCostString = String.valueOf(orderOperations.calculateTotalOrderCost(order.getOrderID(), connection));
        totalCost.setText(totalCostString);

        panel.add(totalCost);
        // Add components to the panel
        panel.add(confirmOrder);
        panel.add(delete);


        // Create an ActionListener for the view buttons
        confirmOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserMainView userMainView = null;
                BankDetailsView bankDetailsView = null;
                try {
                    String userId = order.getUserID();
                    if(testOperations.isUserHaveBankDetails(userId, connection)){
                        System.out.println("CONFIRMING");
                        orderOperations.updateOrderStatus(order.getOrderID(), "confirmed", connection);
                        userMainView = new UserMainView(connection, user);
                        
                        TrainsOfSheffield.getPanel().removeAll();
                        TrainsOfSheffield.getPanel().add(bankDetailsView, BorderLayout.CENTER);
                        TrainsOfSheffield.getPanel().revalidate();

                    }else {
                        // ask josh to link bank details page
                        System.out.println("goig bank");
                        bankDetailsView = new BankDetailsView(connection, order, user, true);
                        
                        TrainsOfSheffield.getPanel().removeAll();
                        TrainsOfSheffield.getPanel().add(bankDetailsView, BorderLayout.CENTER);
                        TrainsOfSheffield.getPanel().revalidate();

                    }
                    
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                int selectedRow = basketTable.getSelectedRow();

                int orderLineId = Integer.parseInt(basketTable.getValueAt(selectedRow, 0).toString());

                try {          
                    orderOperations.deleteOrderLine(orderLineId, connection);
                    model.removeRow(selectedRow);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });



    }

}