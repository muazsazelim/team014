package com.sheffield.views;

import javax.swing.*;

import com.sheffield.model.DatabaseConnectionHandler;

import java.sql.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewItemView extends JFrame {
    public NewItemView(Connection connection) throws SQLException {
        this.setTitle("Train of Sheffield");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320, 320);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel header = new JPanel(new BorderLayout());
        this.add(header, BorderLayout.PAGE_START);
        this.add(panel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Add New Item");
        titleLabel.setFont(new Font("Default", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(titleLabel, BorderLayout.PAGE_START);

        JLabel pName = new JLabel("Product Name");
        JLabel bName = new JLabel("Brand Name");
        JLabel rPrice = new JLabel("Retail Price (£)");
        JLabel gType = new JLabel("Gauge Type");
        JLabel pType = new JLabel("Select product type");

        JTextField pNameF = new JTextField();
        JTextField bNameF = new JTextField();
        JTextField rPriceF = new JTextField();
        JTextField gTypeF = new JTextField();

        JRadioButton trackPack = new JRadioButton("Track Pack");
        JRadioButton trackPiece = new JRadioButton("Track Piece");
        JRadioButton locomotive = new JRadioButton("Locomotive");
        JRadioButton trainSet = new JRadioButton("Train Set");
        JRadioButton rollingStock = new JRadioButton("Rolling Stock");
        JRadioButton controller = new JRadioButton("Controller");
        ButtonGroup buttonGroup = new ButtonGroup();

        buttonGroup.add(trackPack);
        buttonGroup.add(trackPiece);
        buttonGroup.add(locomotive);
        buttonGroup.add(trainSet);
        buttonGroup.add(rollingStock);
        buttonGroup.add(controller);

        panel.add(pName);
        panel.add(pNameF);
        panel.add(bName);
        panel.add(bNameF);
        panel.add(rPrice);
        panel.add(rPriceF);
        panel.add(gType);
        panel.add(gTypeF);

        panel.add(pType);
        panel.add(trackPack);
        panel.add(trackPiece);
        panel.add(locomotive);
        panel.add(trainSet);
        panel.add(rollingStock);
        panel.add(controller);

        JScrollPane sp = new JScrollPane(panel);
        this.add(sp);
    }

    public static void main(String[] args) {
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        SwingUtilities.invokeLater(() -> {
            NewItemView newI = null;
            try {
                databaseConnectionHandler.openConnection();

                newI = new NewItemView(databaseConnectionHandler.getConnection());
                newI.setVisible(true);

            } catch (Throwable t) {
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }
}
