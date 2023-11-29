package com.sheffield.views;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class TrainsOfSheffield extends JFrame {

    public static JPanel contentPanel;

    public TrainsOfSheffield () throws SQLException {


        JFrame parent = this;
        parent.setTitle("Train of Sheffield");
        parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        parent.getContentPane().setLayout(new BorderLayout());
        parent.setVisible(true);
        parent.setSize(720,600);
        ImageIcon image = new ImageIcon("logo.png");
        parent.setIconImage(image.getImage()); 


        // Create a JPanel to hold the components
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        parent.add(contentPanel, BorderLayout.CENTER);

    }

    public static JPanel getPanel() {
        return contentPanel;
    }

    
}
