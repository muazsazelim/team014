package com.sheffield.views;

import com.sheffield.util.HashedPasswordGenerator;
import com.sheffield.util.TestOperations; // remove this later
import com.sheffield.util.UniqueUserIDGenerator;
import com.sheffield.model.DatabaseConnectionHandler; // remove this later
import com.sheffield.model.DatabaseOperations; // use this instead
import com.sheffield.model.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class BankDetailsView extends JFrame {
    private JTextField bankCardNameField;
    private JTextField holderNameField;
    private JTextField bankCardNumberField;
    private JTextField expiryDateField;
    private JTextField securityCodeField;
    public BankDetailsView(Connection connection) throws SQLException {
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

        //Concise way to write 
        String[] labels = {"Card type:", "Card Holder Name:", "Card Number:", "Expiry Date (MM/YY):", "Security Code:"};
        JTextField[] fields = {bankCardNameField, holderNameField, bankCardNumberField, expiryDateField, securityCodeField};

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
        field = new JTextField(20);
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
                // try{
                //     TestOperations databaseOperations = new TestOperations();
                //     String inputForename = forenameField.getText();
                //     String inputSurname = surnameField.getText();
                //     char[] inputPassword = passwordField.getPassword();
                //     String inputEmail = emailField.getText();
                //     String password = new String(passwordField.getPassword());
                //     String confirmPassword = new String(passwordreenterField.getPassword());
                    
                //     if (!inputForename.isEmpty() && !inputSurname.isEmpty() && !inputEmail.isEmpty() && !password.isEmpty()){
                //         if (password.equals(confirmPassword)) {
                //             User newUser = new User(UniqueUserIDGenerator.generateUniqueUserID(), inputEmail, HashedPasswordGenerator.hashPassword(inputPassword), 0, false, inputForename, inputSurname, "customer");
                //             databaseOperations.insertUser(newUser, connection);
                //             System.out.println("New User created");
                //             System.out.println(newUser);
                //             LoginView.getInstance().setVisible(true);
                //             dispose();
                //         } else {
                //             System.out.println("Passwords do not match");
                //             errorLabel.setVisible(true);
                //             pack();
                //         }
                //     } else {
                //         errorFillLabel.setVisible(true);
                //         pack();
                //     }
                    

                // }catch (Throwable t) {
                //     // Close connection if database crashes.
                //     throw new RuntimeException(t);
                // }

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

            // Create and initialize the LoanTableDisplay view using the database connection
            BankDetailsView = new BankDetailsView(databaseConnectionHandler.getConnection());
            BankDetailsView.setVisible(true);

        } catch (Throwable t) {
            // Close connection if database crashes.
            databaseConnectionHandler.closeConnection();
            throw new RuntimeException(t);
        }
    });
    }

}