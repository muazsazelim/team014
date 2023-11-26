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

public class RegisterView extends JFrame {
    private JTextField forenameField;
    private JTextField surnameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField passwordreenterField;
    public RegisterView (Connection connection) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Train of Sheffield");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320, 400);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        this.add(panel);

        // Set a layout manager for the panel (e.g., GridLayout)
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Create JLabels for username and password
        JLabel forenameLabel = new JLabel("Forename:");
        JLabel surnameLabel = new JLabel("Surname:");
        JLabel passwordLabel = new JLabel("  New Password:");
        JLabel passwordreenterLabel = new JLabel("  Re-enter Password:");
        JLabel emailLabel = new JLabel("  Email:");
        JLabel errorLabel = new JLabel("Passwords do not match");
        JLabel errorLabel2 = new JLabel("Please fill all the field");
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        errorLabel2.setForeground(Color.RED);
        errorLabel2.setVisible(false);

        // Create JTextFields for entering username and password
        forenameField = new JTextField(20);
        surnameField = new JTextField(20);
        emailField = new JTextField(20);
        emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordField = new JPasswordField(20);
        passwordreenterField = new JPasswordField(20);

        // Create a JButton for the register action

        JLabel regLabel1 = new JLabel("  Welcome to Train of Sheffield");
        regLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        forenameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        surnameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel regLabel2 = new JLabel(" where your past journeys are your future destinations.");
        JButton registerButton = new JButton("Register");

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(regLabel1, gbc);
        // panel.add(new JLabel());
        gbc.gridy = 1;
        gbc.insets = new Insets(0,0,10,0);
        panel.add(regLabel2, gbc);
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);
        gbc.gridy = 3;
        panel.add(passwordField, gbc);
        gbc.gridx = 0;
        panel.add(passwordLabel, gbc);
        gbc.gridy = 4;
        gbc.gridx = 1;
        panel.add(passwordreenterField, gbc);
        gbc.gridx = 0;
        panel.add(passwordreenterLabel, gbc);

        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0,0,0,0);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(forenameLabel, gbc);
        gbc.gridy = 7;
        panel.add(surnameLabel, gbc);
        gbc.gridy = 9;
        panel.add(errorLabel, gbc);
        gbc.gridy = 10;
        panel.add(errorLabel2, gbc);
        gbc.gridy = 11;
        gbc.insets = new Insets(0, 10, 10, 10);
        panel.add(registerButton, gbc);
        gbc.gridy = 6;
        panel.add(forenameField, gbc);
        gbc.gridy = 8;
        panel.add(surnameField, gbc);
        this.getContentPane().add(panel);
        this.pack();
        

        // Create an ActionListener for the register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Inserting new user if the details are valid
                try{
                    TestOperations databaseOperations = new TestOperations();
                    String inputForename = forenameField.getText();
                    String inputSurname = surnameField.getText();
                    char[] inputPassword = passwordField.getPassword();
                    String inputEmail = emailField.getText();
                    String password = new String(passwordField.getPassword());
                    String confirmPassword = new String(passwordreenterField.getPassword());
                    
                    if (!inputForename.isEmpty() && !inputSurname.isEmpty() && !inputEmail.isEmpty() && !password.isEmpty()){
                        if (password.equals(confirmPassword)) {
                            User newUser = new User(UniqueUserIDGenerator.generateUniqueUserID(), inputEmail, HashedPasswordGenerator.hashPassword(inputPassword), 0, false, inputForename, inputSurname, "customer");
                            //databaseOperations.insertUser(newUser, connection);
                            System.out.println("New User created");
                            System.out.println(newUser);
                            LoginView.getInstance().setVisible(true);
                            dispose();
                        } else {
                            System.out.println("Passwords do not match");
                            errorLabel.setVisible(true);
                            pack();
                        }
                    } else {
                        errorLabel2.setVisible(true);
                        pack();
                    }
                    

                }catch (Throwable t) {
                    // Close connection if database crashes.
                    throw new RuntimeException(t);
                }

            }
        });
    }


    public static void main(String[] args) {
    DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
    
    // Execute the Swing GUI application on the Event Dispatch Thread
    SwingUtilities.invokeLater(() -> {
        RegisterView registerView = null;
        try {
            // Open a database connection
            databaseConnectionHandler.openConnection();

            // Create and initialize the LoanTableDisplay view using the database connection
            registerView = new RegisterView(databaseConnectionHandler.getConnection());
            registerView.setVisible(true);

        } catch (Throwable t) {
            // Close connection if database crashes.
            databaseConnectionHandler.closeConnection();
            throw new RuntimeException(t);
        }
    });
    }

}