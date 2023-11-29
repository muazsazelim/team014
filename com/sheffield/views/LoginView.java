    package com.sheffield.views;

    import com.sheffield.model.DatabaseOperations;
import com.sheffield.util.TestOperations;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.sql.Connection;
    import java.sql.SQLException;
    import java.util.Arrays;

    public class LoginView extends JPanel {
        private JTextField emailField;
        private JPasswordField passwordField;
        private static LoginView instance;
        public LoginView (Connection connection) throws SQLException {


            // Create a JPanel to hold the components
            JPanel contentPanel = this;
            contentPanel.setLayout(new BorderLayout());

            instance = this;

            // Create a JPanel to hold the components
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            contentPanel.add(panel, BorderLayout.CENTER);

            // Set a layout manager for the panel (e.g., GridLayout)
            /*
            panel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            */

            JLabel titleLabel = new JLabel();
            titleLabel.setText("Trains of Sheffield");
            titleLabel.setHorizontalAlignment(JLabel.CENTER);
            titleLabel.setFont(new Font("MONOSPACED", Font.BOLD, 24));
            titleLabel.setBorder(new EmptyBorder(10,10,10,10));

            panel.add(titleLabel, BorderLayout.NORTH);


            JLabel emailLabel = new JLabel("  Email:");
            JLabel passwordLabel = new JLabel("  Password:");
            JLabel errorFillLabel = new JLabel("Please fill all the field");
            errorFillLabel.setForeground(Color.RED);
            errorFillLabel.setVisible(false);

            emailField = new JTextField(20);
            passwordField = new JPasswordField(20);

            JPanel loginDetails = new JPanel();
            loginDetails.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            panel.add(loginDetails, BorderLayout.CENTER);




            // Create a JButton for the login action
            JButton loginButton = new JButton("Login");

            JLabel registerLabel = new JLabel("  New user? Sign up now!");
            JButton registerButton = new JButton("Register");

            
            // Add components to the panel
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(0, 0, 10, 0);
            gbc.insets = new Insets(0, 0, 0, 0);
            // panel.add(new JLabel());
            gbc.gridy = 1;
            loginDetails.add(emailLabel, gbc);
            gbc.gridx = 1;
            loginDetails.add(emailField, gbc);
            gbc.gridy = 2;
            loginDetails.add(passwordField, gbc);
            gbc.gridx = 0;
            loginDetails.add(passwordLabel, gbc);
            // panel.add(new JLabel());
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            gbc.insets = new Insets(10, 0, 10, 0);
            loginDetails.add(loginButton, gbc);
            gbc.gridy = 6;
            gbc.insets = new Insets(10, 0, 0, 0);
            loginDetails.add(registerLabel, gbc);
            gbc.gridy = 5;
            loginDetails.add(errorFillLabel, gbc);
            gbc.gridy = 8;
            loginDetails.add(registerButton, gbc);
            
            /*

            JLabel titleLabel = new JLabel();
            titleLabel.setText("Trains of Sheffield");
            titleLabel.setHorizontalAlignment(JLabel.CENTER);
            titleLabel.setFont(new Font("MONOSPACED", Font.BOLD, 24));
            titleLabel.setBorder(new EmptyBorder(10,10,10,10));

            panel.add(titleLabel, BorderLayout.NORTH);


            JLabel emailLabel = new JLabel("  Email:");
            emailLabel.setFont(new Font("MONOSPACED", Font.PLAIN, 18));

            JLabel passwordLabel = new JLabel("  Password:");
            passwordLabel.setFont(new Font("MONOSPACED", Font.PLAIN, 18));

            JLabel errorFillLabel = new JLabel("Please fill all the field");
            errorFillLabel.setForeground(Color.RED);
            errorFillLabel.setVisible(false);

            emailField = new JTextField(20);
            emailField.setFont(new Font("MONOSPACED", Font.PLAIN, 18));

            passwordField = new JPasswordField(20);
            passwordField.setFont(new Font("MONOSPACED", Font.PLAIN, 18));


            JPanel loginContainer = new JPanel();
            JPanel loginDetails = new JPanel();
            loginDetails.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;

            loginContainer.add(loginDetails);

            panel.add(loginContainer, BorderLayout.CENTER);





            // Create a JButton for the login action
            JButton loginButton = new JButton("Login");
            loginButton.setFont(new Font("MONOSPACED", Font.BOLD, 18));


            JLabel registerLabel = new JLabel("  New user? Sign up now!");
            registerLabel.setFont(new Font("MONOSPACED", Font.PLAIN, 18));

            JButton registerButton = new JButton("Register");
            registerButton.setFont(new Font("MONOSPACED", Font.BOLD, 18));



            gbc.ipady = 20;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(0,5, 0,5);
            loginDetails.add(emailLabel, gbc);
            gbc.gridx = 1;
            loginDetails.add(emailField, gbc);
            gbc.gridy = 1;
            loginDetails.add(passwordField, gbc);
            gbc.gridx = 0;
            gbc.insets = new Insets(0,5, 0,5);
            loginDetails.add(passwordLabel, gbc);
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.insets = new Insets(10, 0, 0, 0);
            loginDetails.add(loginButton, gbc);
            gbc.gridy = 3;
            gbc.insets = new Insets(10, 0, 0, 0);
            loginDetails.add(registerLabel, gbc);
            gbc.gridy = 4;
            loginDetails.add(errorFillLabel, gbc);
            gbc.gridy = 5;
            loginDetails.add(registerButton, gbc);
            

            */
            

            // Create an ActionListener for the login button
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String email = emailField.getText();
                    char[] passwordChars = passwordField.getPassword();
                    System.out.println(email);
                    System.out.println(new String(passwordChars));
                    if (!email.isEmpty() && !(passwordChars == null) && !(passwordChars.length == 0)){
                        DatabaseOperations databaseOperations = new DatabaseOperations();
                        if (databaseOperations.verifyLogin(connection, email, passwordChars)) {

                            TestOperations testOperations = new TestOperations();

                            UserMainView userMainView = null;
                            try {
                                userMainView = new UserMainView(connection, testOperations.getUser(email, connection));

                                TrainsOfSheffield.getPanel().removeAll();
                                TrainsOfSheffield.getPanel().add(userMainView, BorderLayout.CENTER);
                                TrainsOfSheffield.getPanel().revalidate();

                            } catch (Throwable t) {
                                throw new RuntimeException(t);
                            }
                        }
                        // Secure disposal of the password
                        Arrays.fill(passwordChars, '\u0000');
                    } else{
                        errorFillLabel.setVisible(true);
                        contentPanel.revalidate();
                    }
                    

                }
            });



            // Create an ActionListener for the register button
            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    RegisterView registerView = null;
                    try {
                        registerView = new RegisterView(connection);
                        TrainsOfSheffield.getPanel().removeAll();
                        TrainsOfSheffield.getPanel().add(registerView, BorderLayout.CENTER);
                        TrainsOfSheffield.getPanel().revalidate();
        
                    } catch (Throwable t) {
                        throw new RuntimeException(t);
                    }
                    System.out.println("New User, opening register view");
                }
            });
        }

        public static LoginView getInstance() {
            return instance;
        }
    }
