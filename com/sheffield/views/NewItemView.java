package com.sheffield.views;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.sheffield.model.DatabaseConnectionHandler;
import com.sheffield.model.user.User;

public class NewItemView extends JFrame {

    public NewItemView(Connection connection) throws SQLException { // add user

        this.setTitle("Train of Sheffield");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320, 320);

        this.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        JPanel header = new JPanel(new BorderLayout());
        this.add(header, BorderLayout.NORTH);
        this.add(panel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Add New Item");
        titleLabel.setFont(new Font("Default", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        header.add(titleLabel, BorderLayout.PAGE_START);

        JLabel pName = new JLabel("Product Name: ");
        JLabel bName = new JLabel("Brand Name: ");
        JLabel rPrice = new JLabel("Retail Price (£): ");
        JLabel gType = new JLabel("Gauge Type: ");
        JLabel pType = new JLabel("Product Type");
        JLabel pCode = new JLabel("Product Code (Enter 3-5 Number(s) only): ");

        Font font = new Font("Default", Font.BOLD, 13);
        pName.setFont(font);
        bName.setFont(font);
        rPrice.setFont(font);
        gType.setFont(font);
        pType.setFont(font);
        pCode.setFont(font);

        JTextField pNameF = new JTextField();
        JTextField bNameF = new JTextField();
        JTextField rPriceF = new JTextField();
        JTextField pCodeF = new JTextField();

        ButtonGroup g = new ButtonGroup();
        JRadioButton gauge1 = new JRadioButton("OO Gauge");
        JRadioButton gauge2 = new JRadioButton("TT Gauge");
        JRadioButton gauge3 = new JRadioButton("N Gauge");

        g.add(gauge1);
        g.add(gauge2);
        g.add(gauge3);

        ButtonGroup t = new ButtonGroup();
        JRadioButton p1 = new JRadioButton("Track Pack");
        JRadioButton p2 = new JRadioButton("Track Piece");
        JRadioButton p3 = new JRadioButton("Locomotive");
        JRadioButton p4 = new JRadioButton("Train Set");
        JRadioButton p5 = new JRadioButton("Rolling Stock");
        JRadioButton p6 = new JRadioButton("Controller");

        t.add(p1);
        t.add(p2);
        t.add(p3);
        t.add(p4);
        t.add(p5);
        t.add(p6);

        JButton addProduct = new JButton("Add Product");

        // panel starts here
        panel.add(Box.createVerticalStrut(10));

        panel.add(pName);
        panel.add(pNameF);

        panel.add(Box.createVerticalStrut(10));

        panel.add(bName);
        panel.add(bNameF);

        panel.add(Box.createVerticalStrut(10));

        panel.add(rPrice);
        panel.add(rPriceF);

        panel.add(Box.createVerticalStrut(10));

        panel.add(gType);
        panel.add(Box.createVerticalStrut(10));
        panel.add(gauge1);
        panel.add(gauge2);
        panel.add(gauge3);

        panel.add(Box.createVerticalStrut(10));

        panel.add(pType);
        panel.add(Box.createVerticalStrut(10));
        panel.add(p1);
        panel.add(p2);
        panel.add(p3);
        panel.add(p4);
        panel.add(p5);
        panel.add(p6);

        panel.add(Box.createVerticalStrut(10));

        panel.add(pCode);
        panel.add(pCodeF);

        panel.add(Box.createVerticalStrut(10));

        panel.add(addProduct);

        panel.add(Box.createVerticalStrut(10));

        JScrollPane sp = new JScrollPane(panel);
        this.add(sp);

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        // button functions

        addProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String pN = pNameF.getText();
                    String bN = bNameF.getText();
                    String rP = rPriceF.getText();
                    String pC = pCodeF.getText();
                    Integer.valueOf(pC);
                    String gaugeType = "";
                    String pType = "";
                    String pTID = "";
                    String childQ = "";
                    String tName = "";
                    String p = "";

                    if (gauge1.isSelected()) {
                        gaugeType = "OO Gauge";
                    } else if (gauge2.isSelected()) {
                        gaugeType = "TT Gauge";
                    } else if (gauge3.isSelected()) {
                        gaugeType = "N Gauge";
                    }

                    if (p1.isSelected()) {
                        pType = "Track Pack";
                        pTID = "trackPackID";
                        childQ = "INSERT INTO Track_Pack VALUES (DEFAULT)";
                        tName = "Track_Pack";
                        p = "P";
                    } else if (p2.isSelected()) {
                        pType = "Track Piece";
                        pTID = "trackPieceID";
                        childQ = "INSERT INTO Track_Piece (trackType) VALUES (New Type)";
                        tName = "Track_Piece";
                        p = "R";
                    } else if (p3.isSelected()) {
                        pType = "Locomotive";
                        pTID = "locomotiveID";
                        childQ = "INSERT INTO Locomotive (ERACode, DCCCode) VALUES ('Era1-11', 'analogue')";
                        tName = "Locomotive";
                        p = "L";
                    } else if (p4.isSelected()) {
                        pType = "Train Set";
                        pTID = "trainSetID";
                        childQ = "INSERT INTO Train_Set (ERACode, controllerID) VALUES ('Era1-11', 1)";
                        tName = "Train_Set";
                        p = "M";
                    } else if (p5.isSelected()) {
                        pType = "Rolling Stock";
                        pTID = "rollingStockID";
                        childQ = "INSERT INTO Rolling_Stock (ERACode, rsType) VALUES ('Era1-11', 'wagon')";
                        tName = "Rolling_Stock";
                        p = "S";
                    } else if (p6.isSelected()) {
                        pType = "Controller";
                        pTID = "controllerID";
                        childQ = "INSERT INTO Controller (digital) VALUES (1)";
                        tName = "Controller";
                        p = "C";
                    }

                    PreparedStatement childP = connection.prepareStatement(childQ);
                    childP.executeUpdate();

                    String cQ = "SELECT COUNT(*) FROM " + tName;
                    PreparedStatement cQP = connection.prepareStatement(cQ);
                    ResultSet c = cQP.executeQuery();

                    int id = 0;
                    while (c.next()) {
                        id = c.getInt(1);
                    }

                    String pLong = p + pC;

                    Double rPN = Double.valueOf(rP);

                    String addQ = "INSERT INTO Product (productName, brandName, retailPrice, gaugeType, " + pTID
                            + ", productType, productCo) VALUES (?, ?, ?, ?, ?, ?, ?)";

                    PreparedStatement q = connection.prepareStatement(addQ);
                    q.setString(1, pN);
                    q.setString(2, bN);
                    q.setDouble(3, rPN);
                    q.setString(4, gaugeType);
                    q.setInt(5, id);
                    q.setString(6, pType);
                    q.setString(7, pLong);

                    q.executeUpdate();

                    String pMax = "SELECT COUNT(*) FROM Product";
                    PreparedStatement pMa = connection.prepareStatement(pMax);
                    ResultSet pM = pMa.executeQuery();

                    int pIM = 0;

                    while (pM.next()) {
                        pIM = pM.getInt(1);
                    }

                    String addI = "INSERT INTO Inventory (ProductID, Quantity) VALUES (" + pIM + ", 0)";
                    System.out.println(addI);
                    PreparedStatement add = connection.prepareStatement(addI);
                    add.executeUpdate();

                } catch (Throwable t) {

                    JOptionPane.showMessageDialog(panel,
                            "You have error in the product information, please check again.");
                    t.printStackTrace();
                }
            }
        });

    }

    public static void main(String[] args) {
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        SwingUtilities.invokeLater(() -> {
            NewItemView nv = null;
            try {
                databaseConnectionHandler.openConnection();

                nv = new NewItemView(databaseConnectionHandler.getConnection());
                nv.setVisible(true);

            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        });
    }

}
