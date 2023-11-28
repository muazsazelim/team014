package com.sheffield.views;

import javax.swing.*;
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
        this.add(panel);
    }
}
