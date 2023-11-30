package com.sheffield.views;

import com.sheffield.util.TestOperations; // remove this later
import com.sheffield.model.DatabaseConnectionHandler; // remove this later
import com.sheffield.model.DatabaseOperations; // use this instead
import com.sheffield.model.user.BankDetails;
import com.sheffield.model.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class BankDetailsView extends JFrame {
    private JTextField bankCardNameField = new JTextField(20);
    private JTextField holderNameField = new JTextField(20);
    private JTextField bankCardNumberField = new JTextField(20);
    private JTextField expiryDateField = new JTextField(20);
    private JTextField securityCodeField = new JTextField(20);
    public BankDetailsView(Connection connection, User user) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Bank Details Update");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320, 400);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        this.add(panel);

        // Set a layout manager for the panel (e.g., GridLayout)
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        // Create JLabels for bank details
        JLabel introLabel1 = new JLabel("Please fill the required details");
        JLabel introLabel2 = new JLabel("to start checking out your order");
        introLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        introLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel errorFillLabel = new JLabel("Please fill all the field");
        errorFillLabel.setForeground(Color.RED);
        errorFillLabel.setVisible(false);
        JButton submitButton = new JButton("Submit");

        // Add components to the panel

        // panel.add(new JLabel());
        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth= 3;
        panel.add(introLabel1, gbc);
        gbc.gridy = 1;
        panel.add(introLabel2, gbc);
        gbc.gridy = 8;
        panel.add(submitButton, gbc);
        gbc.gridy = 7;
        panel.add(errorFillLabel, gbc);

        //Concise way to write 
        String[] labels = {"Bank Name:", "Card Holder Name:", "Card Number:", "Expiry Date (MM/YY):", "Security Code:"};
        JTextField[] fields = {bankCardNumberField, bankCardNameField, holderNameField, expiryDateField, securityCodeField};

        gbc.gridwidth= 1;
        int row = 2;
        for (String label : labels) {
        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(new JLabel(label), gbc);
        }

        row = 2;
        for (JTextField field : fields) {
        gbc.gridx = 1;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        panel.add(field, gbc);
        gbc.gridwidth = 1;
        }

        // end

        this.getContentPane().add(panel);
        this.pack();
        

        // Create an ActionListener for the register button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Inserting new user if the details are valid
                try{
                    TestOperations databaseOperations = new TestOperations();
                    ArrayList<String> inputList = new ArrayList<>();
                    boolean listNotNull = true;
                    for (JTextField field1 : fields) {
                        if (field1 == null){
                            listNotNull= false;
                        }else{
                            System.out.println(field1.getText().length());
                            inputList.add(field1.getText()) ;
                        }
                    }
                    if (listNotNull){
                        if (!inputList.get(0).isEmpty() && !inputList.get(1).isEmpty() && !inputList.get(2).isEmpty() && !inputList.get(3).isEmpty() && !inputList.get(4).isEmpty()){
                            //dialog upon valid card
                            BankDetails newBankDetails = new BankDetails(inputList.get(2), inputList.get(0), inputList.get(1), inputList.get(3), inputList.get(4));
                            databaseOperations.insertBankDetails(newBankDetails, connection, user);
                            System.out.println("New Bank details created");
                            System.out.println(newBankDetails); //hide this later
                            JOptionPane.showMessageDialog(null, "Bank Details submited");
                            dispose(); //dialog here
                        } else {
                            errorFillLabel.setVisible(true);
                            pack();
                        }
                    }else {
                        errorFillLabel.setVisible(true);
                        pack();
                    }
                }catch (Throwable t) {
                    // Close connection if database crashes.
                    throw new RuntimeException(t);
                }

                //end of button action

            }
        });
    }


    public static void main(String[] args) {
    DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
    
    // Execute the Swing GUI application on the Event Dispatch Thread
    SwingUtilities.invokeLater(() -> {
        BankDetailsView BankDetailsView = null;
        try {
            // Open a database connection
            databaseConnectionHandler.openConnection();

            // Now it's linked to the current user login. Cannot run from this 'main'
            //BankDetailsView = new BankDetailsView(databaseConnectionHandler.getConnection(), user);
            //BankDetailsView.setVisible(true);

        } catch (Throwable t) {
            // Close connection if database crashes.
            databaseConnectionHandler.closeConnection();
            throw new RuntimeException(t);
        }
    });
    }

}