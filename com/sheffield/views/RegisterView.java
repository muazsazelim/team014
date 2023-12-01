package com.sheffield.views;

import com.sheffield.util.HashedPasswordGenerator;
import com.sheffield.util.TestOperations; // remove this later
import com.sheffield.util.UniqueUserIDGenerator;
import com.sheffield.model.Address;
import com.sheffield.model.DatabaseConnectionHandler; // remove this later
import com.sheffield.model.DatabaseOperations; // use this instead
import com.sheffield.model.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class RegisterView extends JPanel {
    private JTextField forenameField = new JTextField(20);
    private JTextField surnameField = new JTextField(20);
    private JTextField emailField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);
    private JPasswordField passwordreenterField = new JPasswordField(20);
    private JTextField houseNumberField = new JTextField(20);
    private JTextField roadNameField = new JTextField(20);
    private JTextField postcodeField = new JTextField(20);
    private JTextField cityNameField = new JTextField(20);
    public RegisterView (Connection connection) throws SQLException {


        // Create a JPanel to hold the components
        JPanel contentPanel = this;
        contentPanel.setLayout(new BorderLayout());


        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        contentPanel.add(panel, BorderLayout.CENTER);

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());

        contentPanel.add(header, BorderLayout.NORTH);

        // Set a layout manager for the panel (e.g., GridLayout)
        GridBagConstraints gbc = new GridBagConstraints();

        // Create JLabels for username and password
        JLabel errorLabel = new JLabel("Passwords do not match");
        JLabel errorLabel2 = new JLabel("Please fill all the field");
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        errorLabel2.setForeground(Color.RED);
        errorLabel2.setVisible(false);

        // Create a JButton for the register action

        JLabel regLabel1 = new JLabel("  Welcome to Train of Sheffield");
        regLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel regLabel2 = new JLabel(" where your past journeys are your future destinations.");
        JButton registerButton = new JButton("Register");

        JButton backButton = new JButton("Back");

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(regLabel1, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 1;
        panel.add(regLabel2, gbc);
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.gridy = 22;
        panel.add(errorLabel, gbc);
        gbc.gridy = 23;
        panel.add(errorLabel2, gbc);
        gbc.gridy = 24;
        panel.add(registerButton, gbc);
        
        header.add(backButton, BorderLayout.WEST);


        //copy pasta start
        String[] labels = {"Email:", "New Password:", "Re-enter password:", "Forename:", "Surname:", "House Number:", "Road Name:", "City Name:", "Postcode:"};
        JTextField[] fields = {emailField, passwordField, passwordreenterField, forenameField, surnameField, houseNumberField, roadNameField, cityNameField, postcodeField};

        gbc.gridwidth= 2;
        int row = 2;
        for (String label : labels) {
        //gbc.gridx = 0;
        row += 2;
        gbc.gridy = row;
        JLabel insertLabel = new JLabel(label);
        insertLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(insertLabel, gbc);
        }
        
        gbc.insets = new Insets(0, 5, 0, 5);
        row = 3;
        for (JTextField field : fields) {
        //gbc.gridx = 1;
        row+=2;
        gbc.gridy = row;
        panel.add(field, gbc);
        }

        // end of organising
        

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
                    
                    if (!inputForename.isEmpty() && !inputSurname.isEmpty() && !inputEmail.isEmpty() && !password.isEmpty() && !(houseNumberField.getText().isEmpty()) && !(roadNameField.getText().isEmpty()) && !(cityNameField.getText().isEmpty()) && !(postcodeField.getText().isEmpty())){
                        if (password.equals(confirmPassword)) {
                            User newUser = new User(UniqueUserIDGenerator.generateUniqueUserID(), inputEmail, HashedPasswordGenerator.hashPassword(inputPassword), 0, false, inputForename, inputSurname, "customer");
                            databaseOperations.insertUser(newUser, connection);
                            System.out.println("New User created");
                            System.out.println(newUser);
                            Address newAddress = new Address(houseNumberField.getText(), roadNameField.getText(), cityNameField.getText(), postcodeField.getText(), newUser.getuserId());
                            databaseOperations.insertAddress(newAddress, connection);
                            LoginView loginView = null;
                            try {
                                loginView = new LoginView(connection);
                                TrainsOfSheffield.getPanel().removeAll();
                                TrainsOfSheffield.getPanel().add(loginView, BorderLayout.CENTER);
                                TrainsOfSheffield.getPanel().revalidate();
                
                            } catch (Throwable t) {
                                throw new RuntimeException(t);
                            }
                        } else {
                            System.out.println("Passwords do not match");
                            errorLabel.setVisible(true);
                            contentPanel.revalidate();

                        }
                    } else {
                        errorLabel2.setVisible(true);
                        contentPanel.revalidate();
                    }
                    

                }catch (Throwable t) {
                    // Close connection if database crashes.
                    throw new RuntimeException(t);
                }

            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Went to LoginView");

                //dispose();
                LoginView loginView = null;
                try {
                    loginView = new LoginView(connection);
                    //userDetailsView.setVisible(true);
                    TrainsOfSheffield.getPanel().removeAll();
                    TrainsOfSheffield.getPanel().add(loginView, BorderLayout.CENTER);
                    TrainsOfSheffield.getPanel().revalidate();
    
                } catch (Throwable t) {
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