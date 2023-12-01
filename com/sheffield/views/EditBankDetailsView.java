package com.sheffield.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import com.sheffield.model.Address;
import com.sheffield.model.user.BankDetails;
import com.sheffield.model.user.User;
import com.sheffield.util.TestOperations;


public class EditBankDetailsView extends JPanel {

    public EditBankDetailsView (Connection connection, User user) throws SQLException {

        // JFrame parent = this;
        // parent.setTitle("Train of Sheffield");
        // parent.getContentPane().setLayout(new BorderLayout());
        // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // parent.setVisible(true);
        // parent.setSize(720,600);

        // Create a JPanel to hold the components
        JPanel contentPanel = this;
        contentPanel.setLayout(new BorderLayout());
        //parent.add(contentPanel, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        contentPanel.add(panel, BorderLayout.CENTER);

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        contentPanel.add(header, BorderLayout.NORTH);



        
        // Create buttons that links to other pages from default page
        JLabel changeDetails;
        changeDetails = new JLabel("Edit Bank Details"); 
        
        JPanel userDetails = new JPanel();
        userDetails.setLayout(new BoxLayout(userDetails, BoxLayout.PAGE_AXIS));

        // Add components to the panel
        panel.add(changeDetails);
        panel.add(userDetails);
        

        TestOperations testOperations = new TestOperations();
        BankDetails bankDetails = testOperations.getBankDetails(user.getuserId(), connection);


            
        JLabel cardNum = new JLabel("Card Number - "+bankDetails.getcardNumber());
        userDetails.add(cardNum);

        JLabel bankName = new JLabel("Bank Name - "+bankDetails.getbankName()); 
        userDetails.add(bankName);

        JLabel holderName = new JLabel("Holder Name - "+bankDetails.getholderName()); 
        userDetails.add(holderName);

        JLabel cardExpDate = new JLabel("Expired Date - "+ bankDetails.getcardExpDate()); 
        userDetails.add(cardExpDate);

        JLabel secCode = new JLabel("Security Code - "+ bankDetails.getsecCode()); 
        userDetails.add(secCode);
        
        JButton updateButton = new JButton("Update");
        userDetails.add(updateButton);

        JButton backButton = new JButton("Back");
        header.add(backButton, BorderLayout.WEST);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Change Details View");

                //dispose();
                BankDetailsView loginView = null;
                try {
                    loginView = new BankDetailsView(connection, user);
                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(loginView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }


            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to Change Details View");

                //dispose();
                UserDetailsView userMainView = null;
                try {
                    userMainView = new UserDetailsView(connection, user);
                    //userDetailsView.setVisible(true);
                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(userMainView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();
    
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
        });

        panel.revalidate();
        panel.repaint();    


    }
    
}
