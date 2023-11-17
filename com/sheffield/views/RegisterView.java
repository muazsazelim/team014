    package com.sheffield.views;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.sql.Connection;
    import java.sql.SQLException;

    public class RegisterView extends JFrame {
        private JTextField usernameField;
        private JTextField emailField;
        private JPasswordField passwordField;
        public RegisterView (Connection connection) throws SQLException {
            // Create the JFrame in the constructor
            this.setTitle("Train of Sheffield");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(320, 320);

            // Create a JPanel to hold the components
            JPanel panel = new JPanel();
            this.add(panel);

            // Set a layout manager for the panel (e.g., GridLayout)
            panel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            // Create JLabels for username and password
            JLabel usernameLabel = new JLabel("  Username:");
            JLabel passwordLabel = new JLabel("  New Password:");
            JLabel emailLabel = new JLabel("  Email:");

            // Create JTextFields for entering username and password
            usernameField = new JTextField(20);
            emailField = new JTextField(20);
            emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
            passwordField = new JPasswordField(20);

            // Create a JButton for the register action

            JLabel regLabel1 = new JLabel("  Welcome to Train of Sheffield");
            regLabel1.setHorizontalAlignment(SwingConstants.CENTER);
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
            panel.add(usernameLabel, gbc);
            gbc.gridx = 1;
            panel.add(usernameField, gbc);
            gbc.gridy = 3;
            panel.add(passwordField, gbc);
            gbc.gridx = 0;
            panel.add(passwordLabel, gbc);

            gbc.gridy = 4;
            gbc.gridwidth = 2;
            gbc.insets = new Insets(0,0,0,0);
            gbc.anchor = GridBagConstraints.CENTER;
            
            panel.add(emailLabel, gbc);
            gbc.gridy = 5;
            gbc.insets = new Insets(10, 10, 0, 10);
            panel.add(emailField, gbc);
            gbc.gridy = 8;
            panel.add(registerButton, gbc);

            this.getContentPane().add(panel);
            this.pack();
            

            // Create an ActionListener for the register button
            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("New User created");
                }
            });
        }
    }
