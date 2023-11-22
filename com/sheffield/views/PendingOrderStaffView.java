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

public class PendingOrderStaffView extends JFrame {
    private JButton archivedOrders;
    private JButton orderHistory;
    private JButton products;
    private JTable basketTable;
    private Object[][] data;
    public PendingOrderStaffView (Connection connection) throws SQLException {

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

        //sample data -filter for one customer - needs order no. - date of order(cutomer) -filter for all -  customer detail - email - address (staff)
        data = new Object[][]{
            {"Order1", "11-02-2023", "Mike", "Wazowaki", "mikewazzer@gmail.com", "West Street 24, S10 1TB", "£24", "pending"},
            {"Order2", "12-02-2023", "Albert", "Einstein", "bertto123@gmail.com", "Bank Street 134, S3 7BY", "£15", "pending"},
            {"Order3", "13-02-2023", "Joshva", "Jerry", "theoriginaljoshva101@gmail.com", "65 Edward St, S4 9HY", "£56", "pending"}
        };


        String[] columnNames = {"OrderID", "Date", "Forename", "Surname", "Email", "Delivery Address"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };


        basketTable = new JTable(model);

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
        archivedOrders = new JButton("Archived Orders");
        orderHistory = new JButton("Order History");
        products = new JButton("View Products");

        // Add components to the panel
        panel.add(archivedOrders);
        panel.add(orderHistory);
        panel.add(products);

        this.getContentPane().add(panel, BorderLayout.NORTH);
        this.pack();

        // Create an ActionListener for the view buttons
        archivedOrders.addActionListener(new ActionListener() {
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