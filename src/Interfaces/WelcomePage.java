package Interfaces;

import javax.swing.*;
import java.awt.*;

public class WelcomePage {

    public WelcomePage() {
        // Create the main frame
        JFrame frame = new JFrame("Welcome to Formatini");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);  // Increased size for better visibility
        frame.setLayout(null); // Set layout to null for absolute positioning
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Set the background color of the frame to creamy color
        frame.getContentPane().setBackground(new Color(255, 253, 208)); // Light creamy color

        // Create the label for the welcome message
        JLabel welcomeLabel = new JLabel("Welcome to Formatini", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.BLACK); // Set label text color to black
        welcomeLabel.setBounds(50, 30, 400, 30); // Adjusted the position and width of the label
        frame.add(welcomeLabel);

        // Create the buttons
        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign Up");
        JButton aboutUsButton = new JButton("About Us");

        // Set the button colors (Navy blue background #002147, White text)
        Color navyColor = new Color(0, 33, 71); // #002147 color

        // Set same width for all buttons
        int buttonWidth = 200;
        int buttonHeight = 40;

        // Set button properties
        loginButton.setBackground(navyColor);
        loginButton.setForeground(Color.WHITE);
        signUpButton.setBackground(navyColor);
        signUpButton.setForeground(Color.WHITE);
        aboutUsButton.setBackground(navyColor);
        aboutUsButton.setForeground(Color.WHITE);

        // Position the buttons with proper spacing
        loginButton.setBounds(150, 100, buttonWidth, buttonHeight); // Position login button
        signUpButton.setBounds(150, 150, buttonWidth, buttonHeight); // Position sign-up button
        aboutUsButton.setBounds(150, 200, buttonWidth, buttonHeight); // Position About Us button

        // Add action listeners to buttons
        loginButton.addActionListener(e -> {
            // Navigate to login page
            new LoginPage(); // Assuming you have a LoginPage class
            frame.dispose(); // Close the welcome page
        });

        signUpButton.addActionListener(e -> {
            // Navigate to sign-up page
            new RegistrationPage(); // Assuming you have a RegisterPage class
            frame.dispose(); // Close the welcome page
        });

        aboutUsButton.addActionListener(e -> {
            // Show About Us message
            JOptionPane.showMessageDialog(frame, "Formatini is a platform for online formations. Join us to enhance your skills!");
        });

        // Add buttons to the frame
        frame.add(loginButton);
        frame.add(signUpButton);
        frame.add(aboutUsButton);

        // Set the frame visible
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Create an instance of the welcome page
        SwingUtilities.invokeLater(() -> new WelcomePage());
    }
}
