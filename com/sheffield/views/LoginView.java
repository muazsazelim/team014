    package com.sheffield.views;

    import com.sheffield.model.DatabaseOperations;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.sql.Connection;
    import java.sql.SQLException;
    import java.util.Arrays;

    public class LoginView extends JFrame {
        private JTextField usernameField;
        private JPasswordField passwordField;
        public LoginView (Connection connection) throws SQLException {
            // Create the JFrame in the constructor
            this.setTitle("Train of Sheffield");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(320, 170);

            // Create a JPanel to hold the components
            JPanel panel = new JPanel();
            this.add(panel);

            // Set a layout manager for the panel (e.g., GridLayout)
            panel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            // Create JLabels for username and password
            JLabel usernameLabel = new JLabel("  Username:");
            JLabel passwordLabel = new JLabel("  Password:");

            // Create JTextFields for entering username and password
            usernameField = new JTextField(20);
            passwordField = new JPasswordField(20);

            // Create a JButton for the login action
            JButton loginButton = new JButton("Login");

            JLabel loginLabel = new JLabel("  Welcome Back !");
            JLabel registerLabel = new JLabel("  New user? Sign up now!");
            JButton registerButton = new JButton("Register");

            // Add components to the panel
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(0, 0, 10, 0);
            panel.add(loginLabel, gbc);
            gbc.insets = new Insets(0, 0, 0, 0);
            // panel.add(new JLabel());
            gbc.gridy = 1;
            panel.add(usernameLabel, gbc);
            gbc.gridx = 1;
            panel.add(usernameField, gbc);
            gbc.gridy = 2;
            panel.add(passwordField, gbc);
            gbc.gridx = 0;
            panel.add(passwordLabel, gbc);
            // panel.add(new JLabel());
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            gbc.insets = new Insets(10, 0, 10, 0);
            panel.add(loginButton, gbc);
            gbc.gridy = 6;
            gbc.insets = new Insets(10, 0, 0, 0);
            panel.add(registerLabel, gbc);
            gbc.gridy = 8;
            panel.add(registerButton, gbc);

            this.getContentPane().add(panel);
            this.pack();
            
            

            

            // Create an ActionListener for the login button
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = usernameField.getText();
                    char[] passwordChars = passwordField.getPassword();
                    System.out.println(username);
                    System.out.println(new String(passwordChars));
                    DatabaseOperations databaseOperations = new DatabaseOperations();
                    System.out.println(databaseOperations.verifyLogin(connection, username, passwordChars));
                    // Secure disposal of the password
                    Arrays.fill(passwordChars, '\u0000');
                    dispose();
                    UserMainView userMainView = null;
                    try {
                        userMainView = new UserMainView(connection);
                        userMainView.setVisible(true);
        
                    } catch (Throwable t) {
                        throw new RuntimeException(t);
                    }

                }
            });

            // Create an ActionListener for the register button
            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    RegisterView registerView = null;
                    try {
                        registerView = new RegisterView(connection);
                        registerView.setVisible(true);
        
                    } catch (Throwable t) {
                        throw new RuntimeException(t);
                    }
                    System.out.println("New User, opening register view");
                }
            });
        }
    }
