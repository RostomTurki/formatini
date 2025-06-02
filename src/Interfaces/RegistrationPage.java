package Interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Connections.ConnectionDBUser;
import Classes.Utilisateur;

public class RegistrationPage {

    private ConnectionDBUser connectionDBUser;

    public RegistrationPage() {
        // Initialize ConnectionDB instance
        connectionDBUser = new ConnectionDBUser();

        // Create frame
        JFrame frame = new JFrame("Registration Interface");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(245, 245, 220)); // Set creamy background

        // Create and add "Welcome" label (Bigger, Bolder text)
        JLabel welcomeLabel = new JLabel("Sign up", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Bigger and bolder font
        welcomeLabel.setBounds(0, 10, 500, 40); // Adjusted position for the bigger font size
        welcomeLabel.setForeground(Color.BLACK); // Ensure the text is black
        frame.add(welcomeLabel);

        // Create and add "Username" label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 60, 100, 25);
        frame.add(usernameLabel);

        // Create and add username text field
        JTextField usernameField = new JTextField();
        usernameField.setBounds(200, 60, 200, 25);
        frame.add(usernameField);

        // Create and add "Email" label
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 100, 100, 25);
        frame.add(emailLabel);

        // Create and add email text field
        JTextField emailField = new JTextField();
        emailField.setBounds(200, 100, 200, 25);
        frame.add(emailField);

        // Create and add "Password" label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 140, 100, 25);
        frame.add(passwordLabel);

        // Create and add password field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(200, 140, 200, 25);
        frame.add(passwordField);

        // Create and add "Show Password" checkbox for password field
        JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setBounds(305, 170, 150, 25);
        showPasswordCheckBox.setBackground(new Color(245, 245, 220)); // Creamy background
        showPasswordCheckBox.setForeground(Color.BLACK); // Black text
        frame.add(showPasswordCheckBox);
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

        // Create and add "Confirm Password" label
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(50, 200, 150, 25);
        frame.add(confirmPasswordLabel);

        // Create and add confirm password field
        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(200, 200, 200, 25);
        frame.add(confirmPasswordField);

        // Create and add "Show Password" checkbox for confirm password field
        JCheckBox showConfirmPasswordCheckBox = new JCheckBox("Show Password");
        showConfirmPasswordCheckBox.setBounds(305, 230, 150, 25);
        showConfirmPasswordCheckBox.setBackground(new Color(245, 245, 220)); // Creamy background
        showConfirmPasswordCheckBox.setForeground(Color.BLACK); // Black text
        frame.add(showConfirmPasswordCheckBox);
        showConfirmPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showConfirmPasswordCheckBox.isSelected()) {
                    confirmPasswordField.setEchoChar((char) 0);
                } else {
                    confirmPasswordField.setEchoChar('*');
                }
            }
        });

        // Create and add radio buttons for "Formateur" and "Etudiant"
        JRadioButton formateurRadioButton = new JRadioButton("Formateur");
        formateurRadioButton.setBounds(200, 270, 100, 25);
        formateurRadioButton.setBackground(new Color(245, 245, 220)); // Creamy background
        formateurRadioButton.setForeground(Color.BLACK); // Black text
        frame.add(formateurRadioButton);

        JRadioButton etudiantRadioButton = new JRadioButton("Etudiant");
        etudiantRadioButton.setBounds(300, 270, 100, 25);
        etudiantRadioButton.setBackground(new Color(245, 245, 220)); // Creamy background
        etudiantRadioButton.setForeground(Color.BLACK); // Black text
        frame.add(etudiantRadioButton);

        // Group radio buttons to allow only one selection
        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(formateurRadioButton);
        roleGroup.add(etudiantRadioButton);

        // Create and add "Sign Up" button (Restored to the original size and setup)
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(150, 330, 100, 30); // Restored to original size
        signUpButton.setBackground(new Color(0, 33, 71)); // Navy background color
        signUpButton.setForeground(Color.WHITE); // White text color
        frame.add(signUpButton);

        // Create and add "Cancel" button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(270, 330, 100, 30);
        cancelButton.setBackground(new Color(0, 33, 71)); // Navy background color
        cancelButton.setForeground(Color.WHITE); // White text color
        frame.add(cancelButton);

        // Add action listener to cancel button to navigate to login page
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new LoginPage(); // Navigate to login page
            }
        });

        // Add action listener to sign up button (your existing logic)
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                // Validate input fields
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update the email validation regex
                if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                    JOptionPane.showMessageDialog(frame, "Invalid email format. Please use 'email@example.com'.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if passwords match
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(frame, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if the user already exists in the database
                if (connectionDBUser.userExists(email)) {
                    JOptionPane.showMessageDialog(frame,
                            "Username or email already exists! Please choose a different one.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Get the selected role (Formateur or Etudiant)
                String role = formateurRadioButton.isSelected() ? "Formateur"
                        : (etudiantRadioButton.isSelected() ? "Etudiant" : "");

                // Check if a role is selected
                if (role.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please select a role (Formateur or Etudiant).", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Attempt to register the user in the database
                boolean success = connectionDBUser.registerUser(username, email, password, role);

                if (success) {
                    JOptionPane.showMessageDialog(frame, "Registration successful!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    new LoginPage();
                    new Utilisateur(username, email, password); // Redirect to login page
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "An error occurred while registering the user. Please try again later.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Make frame visible
        frame.setVisible(true);
    }
}
