package com.sheffield.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sheffield.util.TestOperations;
import com.sheffield.model.user.User;


public class ManagerView extends JFrame {

    public ManagerView (Connection connection) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Train of Sheffield");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320,320);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        this.add(panel);

        JPanel staffList = new JPanel();
        staffList.setLayout(new GridLayout(0,2));

        JFrame parent = this;

        parent.getContentPane().setLayout(new BorderLayout());
        panel.setLayout(new GridLayout(0,2));

        
        // Create buttons that links to other pages from default page
        JLabel managerView;
        managerView = new JLabel("Manager View");

        // Add components to the panel
        parent.getContentPane().add(managerView, BorderLayout.NORTH);


        JTextField promote = new JTextField();
        JButton promoteButton = new JButton("Promote");

        panel.add(promote);
        panel.add(promoteButton);

        TestOperations testOperations = new TestOperations();


    
        promoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    testOperations.promoteUserByEmail(promote.getText(), connection);   
                    promote.setText(""); 
                    addStaff(parent,staffList,getStaff(testOperations.getAllUsersObj(connection)), connection, new TestOperations());
                    staffList.revalidate();
                    parent.pack();
                } catch (SQLException error) {
                    System.out.println("SQL Error");;
                } 
            }
        });

        addStaff(parent,staffList,getStaff(testOperations.getAllUsersObj(connection)), connection, new TestOperations());


        parent.getContentPane().add(panel, BorderLayout.CENTER);
        parent.getContentPane().add(staffList, BorderLayout.SOUTH);

        parent.pack();

    }

    public ArrayList<User> getStaff(ArrayList<User> users) {
        ArrayList<User> staff = new ArrayList<User>();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserType().equals("staff")) {
                staff.add(users.get(i));
            }
        }
        return staff;
    }

    public void addStaff(JFrame parent, JPanel staffPanel,ArrayList<User> staff, Connection connection, TestOperations testOperations) {

        staffPanel.removeAll();

        for (int i = 0; i < staff.size(); i++) {
            User user = staff.get(i);
            staffPanel.add(new JLabel("First Name - " + user.getForename() + ", Surname - " + user.getSurname() + ", Email - " + user.getemail()));
            JButton demote = new JButton("Demote");
            demote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    testOperations.demoteUser(user.getuserId(), connection); 
                    addStaff(parent,staffPanel,getStaff(testOperations.getAllUsersObj(connection)), connection, new TestOperations());
                    staffPanel.revalidate();
                    parent.pack();
                    
                } catch (SQLException error) {
                    System.out.println("SQL Error");;
                }                  
            }
            });
            staffPanel.add(demote);
        }

        System.out.println(staff);

    }
    
}

/*
    panel.add(new JLabel("First Name - " + user.getForename() + ", Surname - " + user.getSurname() + ", Email - " +user.getemail()));
    JButton demote = new JButton("Demote");
    demote.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                testOperations.demoteUser(user.getuserId(), connection);    
            } catch (SQLException error) {
                System.out.println("SQL Error");;
            }                  
        }
    });
    panel.add(demote);
 */