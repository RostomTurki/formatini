package Interfaces;

import Connections.ConnectionDBUser;
import Exceptions.UtilisateurNonTrouveException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage {
    private ConnectionDBUser connectionDBUser;

    public LoginPage() {
        // Initialize ConnectionDB instance
        connectionDBUser = new ConnectionDBUser();

        // Create frame
        JFrame frame = new JFrame("Login Interface");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        // Set the background color of the frame to creamy color
        frame.getContentPane().setBackground(new Color(255, 253, 208)); // Light creamy color
        
        // Set button color (Navy blue background #002147, White text)
        Color navyColor = new Color(0, 33, 71); // #002147 color
        // Create and add "Welcome" label
        
        JLabel welcomeLabel = new JLabel("Login", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.BLACK); // Set label text color to black
        welcomeLabel.setBounds(0, 10, 400, 30);
        frame.add(welcomeLabel);

        // Create and add "Email" label
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 60, 100, 25);
        frame.add(emailLabel);

        // Create and add email text field
        JTextField emailField = new JTextField();
        emailField.setBounds(150, 60, 200, 25);
        frame.add(emailField);

        // Create and add "Password" label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 100, 25);
        frame.add(passwordLabel);

        // Create and add password field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 200, 25);
        frame.add(passwordField);

        // Create and add "Show Password" checkbox
        JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setBounds(235, 130, 150, 25);
        showPasswordCheckBox.setBackground(new Color(255, 253, 208)); // Match the creamy background color
        showPasswordCheckBox.setForeground(navyColor); // Set checkbox text color to navy
        frame.add(showPasswordCheckBox);

        // Add action listener to show/hide password
        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('*');
                }
            }
        });

        // Create and add empty message label
        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setBounds(50, 150, 300, 25);
        frame.add(messageLabel);

        // Create and add "Login" button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 180, 90, 30);
        loginButton.setBackground(navyColor);
        loginButton.setForeground(Color.WHITE);
        frame.add(loginButton);

        // Add action listener to login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    // Call checkCredentials method from ConnectionDB to validate user
                    if (connectionDBUser.checkCredentials(email, password)) {
                        // Get the role of the user and navigate accordingly
                        String role = connectionDBUser.getUserRole(email);
                        frame.dispose(); // Close the login page
                        if ("Etudiant".equals(role)) {
                            new EtudiantPage(email); // Open Etudiant page
                        } else if ("Formateur".equals(role)) {
                            new FormateurPage(email); // Open Formateur page
                        }
                    }
                } catch (UtilisateurNonTrouveException ex) {
                    messageLabel.setText(ex.getMessage());
                    messageLabel.setForeground(Color.RED);
                }
            }
        });

        // Create and add "Cancel" button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(155, 180, 90, 30);
        cancelButton.setBackground(navyColor);
        cancelButton.setForeground(Color.WHITE);
        frame.add(cancelButton);

        // Add action listener to cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the login page
                new WelcomePage(); // Open the welcome page
            }
        });

        // Create and add "Sign Up" button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(260, 180, 90, 30);
        signUpButton.setBackground(navyColor);
        signUpButton.setForeground(Color.WHITE);
        frame.add(signUpButton);

        // Add action listener to open registration page
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the login page
                new RegistrationPage(); // Open the registration page
            }
        });

        // Make frame visible
        frame.setVisible(true);
    }
}
