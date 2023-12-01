package com.sheffield.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sheffield.model.user.User;
import com.sheffield.util.TestOperations;


public class ManagerView extends JPanel {

    public ManagerView (Connection connection, User user) throws SQLException {
       

        // Create a JPanel to hold the components
        JPanel contentPanel = this;
        contentPanel.setLayout(new BorderLayout());


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,2));


        JPanel staffList = new JPanel();
        staffList.setLayout(new GridLayout(0,2));



        
        // Create buttons that links to other pages from default page
        JLabel managerView;
        managerView = new JLabel("Manager View");
        managerView.setHorizontalAlignment(JLabel.CENTER);

        // Add components to the panel

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());

        JPanel managerPromo = new JPanel();
        managerPromo.setLayout(new BorderLayout());

        JButton backButton = new JButton("Back");
        

        header.add(managerView, BorderLayout.NORTH);
        header.add(backButton, BorderLayout.WEST);
        managerPromo.add(panel, BorderLayout.NORTH);
        managerPromo.add(staffList,BorderLayout.CENTER);

        contentPanel.add(header, BorderLayout.NORTH);
        contentPanel.add(managerPromo, BorderLayout.CENTER);
        // contentPanel.add(staffList, BorderLayout.SOUTH);
        


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
                    addStaff(staffList,getStaff(testOperations.getAllUsersObj(connection)), connection, new TestOperations());
                    staffList.revalidate();
                } catch (SQLException error) {
                    System.out.println("SQL Error");;
                } 
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to User Main View");

                //dispose();
                UserMainView userMainView = null;
                try {
                    userMainView = new UserMainView(connection, user);
                    //userDetailsView.setVisible(true);
                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(userMainView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
        });


        addStaff(staffList,getStaff(testOperations.getAllUsersObj(connection)), connection, new TestOperations());



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

    public void addStaff(JPanel staffPanel,ArrayList<User> staff, Connection connection, TestOperations testOperations) {

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
                    addStaff(staffPanel,getStaff(testOperations.getAllUsersObj(connection)), connection, new TestOperations());
                    staffPanel.revalidate();
                    staffPanel.repaint();
                    
                } catch (SQLException error) {
                    System.out.println("SQL Error");;
                }                  
            }
            });
            staffPanel.add(demote);
        }

        System.out.println(staff);

    }
    /*
    backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Went to User Main View");

            //dispose();
            UserMainView userMainView = null;
            try {
                userMainView = new UserMainView(connection, user);
                //userDetailsView.setVisible(true);
                TrainsOfSheffield.getPanel().removeAll();
                TrainsOfSheffield.getPanel().add(userMainView, BorderLayout.CENTER);
                TrainsOfSheffield.getPanel().revalidate();

            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }
    });
    */
    
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