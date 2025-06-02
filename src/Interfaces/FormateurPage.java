package Interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import Classes.Utilisateur;
import Classes.Formation;
import Connections.ConnectionDBFormation;
import Connections.ConnectionDBUser;

public class FormateurPage {

    private String email; // Instance variable to store the email

    public FormateurPage(String email) {
        this.email = email;
        ConnectionDBUser dbUser = new ConnectionDBUser();
        // Create the main frame
        JFrame frame = new JFrame("Interface Formateur");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setMaximumSize(new Dimension(1000, 800));
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        
        // Create the welcome panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center-align the text
        welcomePanel.setBackground(new Color(250, 250, 250)); // Navy blue background
        
        JLabel welcomeLabel = new JLabel("Welcome " + dbUser.getUserFromDatabase(email).getNom() + " to formatini");
        welcomeLabel.setForeground(Color.DARK_GRAY); // White text color
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Bold, larger font
        
        welcomePanel.add(welcomeLabel);
        // Create the navigation panel (left-side bar)
        JPanel navBar = new JPanel();
        navBar.setLayout(new BorderLayout());
        navBar.setPreferredSize(new Dimension(200, frame.getHeight()));
        navBar.setBackground(new Color(255, 248, 225)); // Creamy background
        navBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(0x002147))); // Blue border
    
        // Buttons on the nav bar with blue background and white text
        JPanel topButtons = new JPanel();
        topButtons.setLayout(new GridLayout(5, 1, 10, 0)); // Added one more row for Add Formation button
        topButtons.setBackground(new Color(255, 248, 225)); // Creamy background
    
        JButton homeButton = new JButton("Home");
        homeButton.setBackground(new Color(0x002147)); // Blue background
        homeButton.setForeground(Color.WHITE); // White text
        JButton formationsButton = new JButton("My Formations");
        formationsButton.setBackground(new Color(0x002147));
        formationsButton.setForeground(Color.WHITE);
        JButton accountButton = new JButton("Account");
        accountButton.setBackground(new Color(0x002147));
        accountButton.setForeground(Color.WHITE);
        JButton addFormationButton = new JButton("Add Formation");
        addFormationButton.setBackground(new Color(0x002147));
        addFormationButton.setForeground(Color.WHITE);
    
        topButtons.add(homeButton);
        topButtons.add(formationsButton);
        topButtons.add(accountButton);
        topButtons.add(addFormationButton);
    
        // Log out button at the bottom with blue background and white text
        JButton logoutButton = new JButton("Log Out");
        logoutButton.setBackground(new Color(0x002147));
        logoutButton.setForeground(Color.WHITE);
    
        // Add buttons to navBar
        navBar.add(topButtons, BorderLayout.NORTH);
        navBar.add(logoutButton, BorderLayout.SOUTH);
    
        // Create the main panel with CardLayout
        JPanel mainPanel = new JPanel(new CardLayout());
    
        // Create individual panels for each view
        JScrollPane homePanel = createHomePanel();
        JScrollPane formationsPanel = createMyFormationsPanel(email,frame);
        JPanel accountPanel = createAccountPanel(email,frame);
        JPanel ajoutFormationPanel = createAjoutFormationPanel(frame);
    
        // Add panels to the mainPanel with card names
        mainPanel.add(homePanel, "Home");
        mainPanel.add(formationsPanel, "Formations");
        mainPanel.add(accountPanel, "Account");
        mainPanel.add(ajoutFormationPanel, "AddFormation");
    
        // Add action listeners to buttons to switch views
        homeButton.addActionListener(e -> switchView(mainPanel, "Home"));
        formationsButton.addActionListener(e -> switchView(mainPanel, "Formations"));
        accountButton.addActionListener(e -> switchView(mainPanel, "Account"));
        addFormationButton.addActionListener(e -> switchView(mainPanel, "AddFormation"));
    
        logoutButton.addActionListener(e -> {
            int confirmed = JOptionPane.showConfirmDialog(frame, "Are you sure you want to log out?", "Log Out",
                    JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frame, "You have logged out.");
                frame.dispose();
                new LoginPage();
            }
        });
    
        // Add components to the frame
        frame.add(welcomePanel, BorderLayout.NORTH); // Add welcome panel to the top
        frame.add(navBar, BorderLayout.WEST);
        frame.add(mainPanel, BorderLayout.CENTER);
    
        // Set initial view
        switchView(mainPanel, "Home");
    
        // Set frame visible
        frame.setVisible(true);
    }
    

    private JScrollPane createHomePanel() {
        // Main panel that will hold all formation panels vertically
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS)); // Vertical stacking
        homePanel.setBackground(new Color(255, 248, 225)); // Creamy background for the home panel
    
        // Fetch formations from the database
        ConnectionDBFormation db = new ConnectionDBFormation();
        List<Formation> formations = db.getAllFormations();
    
        // Add each formation to the home panel
        for (Formation formation : formations) {
            JPanel formationPanel = createFormationPanel(formation);
            formationPanel.setBackground(new Color(252, 252, 252)); // Creamy background for each formation panel
            formationPanel.setBorder(BorderFactory.createLineBorder(new Color(0x002147), 2)); // Blue border color
            homePanel.add(formationPanel); // Add each panel
            homePanel.add(Box.createVerticalStrut(10)); // Optional: Add spacing between panels
        }
    
        // ScrollPane that contains the homePanel
        JScrollPane scrollPane = new JScrollPane(homePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    
        // Set the viewport size to only display three formation panels
        int formationPanelHeight = 100; // Height of each formation panel
        int visiblePanels = 3; // Number of panels to show
        int totalHeight = visiblePanels * formationPanelHeight + (visiblePanels - 1) * 10; // Include spacing
    
        scrollPane.getViewport().setPreferredSize(new Dimension(0, totalHeight));
    
        return scrollPane; // Return the scrollable panel
    }
    

    private JPanel createFormationPanel(Formation formation) {
        // Create the panel with a fixed height
        JPanel formationPanel = new JPanel();
        formationPanel.setLayout(null); // Manual positioning
        formationPanel.setPreferredSize(new Dimension(0, 80)); // Initial placeholder width
        //formationPanel.setBackground(new Color(255, 255, 255)); // Creamy background color
        formationPanel.setBorder(BorderFactory.createLineBorder(new Color(0x002147), 2)); // Blue border color
    
        // Title label
        JLabel titleLabel = new JLabel("Title: " + formation.getTitre());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(Color.BLACK); // Ensure text is visible against the creamy background
        formationPanel.add(titleLabel);
    
        // Description label
        JLabel descriptionLabel = new JLabel("Description: " + formation.getDescription());
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionLabel.setForeground(Color.BLACK); // Ensure text is visible
        formationPanel.add(descriptionLabel);
    
        // Price label
        JLabel priceLabel = new JLabel("Price: " + formation.getPrix());
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        priceLabel.setForeground(Color.BLACK); // Ensure text is visible
        formationPanel.add(priceLabel);
    
        // Instructor label
        JLabel instructorLabel = new JLabel("Instructor: " + formation.getFormateur());
        instructorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        instructorLabel.setForeground(Color.BLACK); // Ensure text is visible
        formationPanel.add(instructorLabel);
    
        // Dynamically adjust component positions based on panel size
        formationPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int panelWidth = formationPanel.getParent().getWidth();
                int dynamicWidth = (int) (panelWidth * 0.9); // Set width to 90% of parent width
                formationPanel.setPreferredSize(new Dimension(dynamicWidth, 150));
    
                int padding = 20;
                int labelHeight = 20;
                int buttonWidth = 80;
    
                // Set bounds for labels
                titleLabel.setBounds(padding, padding, dynamicWidth - 2 * padding - buttonWidth, labelHeight);
                descriptionLabel.setBounds(padding, padding + 20, dynamicWidth - 2 * padding - buttonWidth, labelHeight);
                priceLabel.setBounds(padding, padding + 40, dynamicWidth - 2 * padding - buttonWidth, labelHeight);
                instructorLabel.setBounds(padding, padding + 60, dynamicWidth - 2 * padding - buttonWidth, labelHeight);
    
                // Set bounds for button (if added in the future)
                // For example, if there's a button, position it on the right
                // SomeButton.setBounds(dynamicWidth - buttonWidth - padding, padding + 20, buttonWidth, 60);
            }
        });
    
        return formationPanel;
    }
    

    private JScrollPane createMyFormationsPanel(String email,JFrame frame) {
        // Get formations where the formateur is the provided email
        ConnectionDBFormation dbFormation = new ConnectionDBFormation();
        List<Formation> userFormations = dbFormation.getFormationsByFormateur(email);
    
        JPanel formationsPanel = new JPanel();
        formationsPanel.setLayout(new BoxLayout(formationsPanel, BoxLayout.Y_AXIS));
        formationsPanel.setBackground(new Color(255, 248, 225)); // Creamy background for the main panel
    
        if (userFormations.isEmpty()) {
            JLabel noFormationsLabel = new JLabel("You have not created any formations yet.");
            noFormationsLabel.setFont(new Font("Arial", Font.BOLD, 16));
            noFormationsLabel.setForeground(Color.BLACK); // Set label text to black
            formationsPanel.add(noFormationsLabel);
        } else {
            for (Formation formation : userFormations) {
                // Create a panel for each formation with BorderLayout
                JPanel formationPanel = new JPanel(new BorderLayout());
                formationPanel.setBackground(new Color(252, 252, 252)); // Creamy background for each formation panel
                formationPanel.setBorder(BorderFactory.createLineBorder(new Color(0x002147), 2)); // Blue border color
                formationPanel.setPreferredSize(new Dimension(0, 80)); // Fixed height
    
                // Left panel: Labels
                JPanel labelsPanel = new JPanel();
                labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));
                labelsPanel.setBackground(new Color(252, 252, 252)); // Creamy background for the labels panel
                labelsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
    
                JLabel titleLabel = new JLabel("Title: " + formation.getTitre());
                titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
                titleLabel.setForeground(Color.BLACK); // Black text color for labels
                JLabel descriptionLabel = new JLabel("Description: " + formation.getDescription());
                descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                descriptionLabel.setForeground(Color.BLACK);
                JLabel priceLabel = new JLabel("Price: " + formation.getPrix());
                priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                priceLabel.setForeground(Color.BLACK);
                JLabel instructorLabel = new JLabel("Instructor: " + formation.getFormateur());
                instructorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                instructorLabel.setForeground(Color.BLACK);
    
                labelsPanel.add(titleLabel);
                labelsPanel.add(descriptionLabel);
                labelsPanel.add(priceLabel);
                labelsPanel.add(instructorLabel);
    
                // Right panel: Buttons
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10)); // Align buttons to the right
                buttonPanel.setBackground(new Color(252, 252, 252)); // Creamy background for button panel
    
                // Delete Button
                JButton deleteButton = new JButton("Delete");
                deleteButton.setBackground(new Color(0x002147)); // Blue button background
                deleteButton.setForeground(Color.WHITE); // White text
                deleteButton.addActionListener(e -> {
                    int confirmed = JOptionPane.showConfirmDialog(formationPanel,
                            "Are you sure you want to delete this formation?", "Delete Formation",
                            JOptionPane.YES_NO_OPTION);
                    if (confirmed == JOptionPane.YES_OPTION) {
                        ConnectionDBFormation db = new ConnectionDBFormation();
                        boolean deleted = db.deleteFormation(formation.getId());
                        if (deleted) {
                            JOptionPane.showMessageDialog(formationPanel, "Formation deleted successfully.");
                            // Optionally, refresh the panel or update the list after deletion
                            frame.dispose();
                            new FormateurPage(email);
                        } else {
                            JOptionPane.showMessageDialog(formationPanel, "Failed to delete formation.");
                        }
                    }
                });
    
                // Edit Button
                JButton editButton = new JButton("Edit");
                editButton.setBackground(new Color(0x002147)); // Blue button background
                editButton.setForeground(Color.WHITE); // White text
                editButton.addActionListener(e -> {
                    showEditFormationDialog(formation,frame); // Call method to edit the formation
                });
    
                buttonPanel.add(editButton);
                buttonPanel.add(deleteButton);
    
                // Add labels panel and button panel to the formation panel
                formationPanel.add(labelsPanel, BorderLayout.CENTER);
                formationPanel.add(buttonPanel, BorderLayout.EAST);
    
                // Add formation panel to the main panel
                formationsPanel.add(formationPanel);
                formationsPanel.add(Box.createVerticalStrut(10)); // Add spacing between formations
            }
        }
    
        JScrollPane scrollPane = new JScrollPane(formationsPanel);
        return scrollPane;
    }
    
    
    
    private void showEditFormationDialog(Formation formation, JFrame frame) {
        // Set custom button styles
        UIManager.put("OptionPane.buttonBackground", new Color(0x002147)); // Blue button background
        UIManager.put("OptionPane.buttonForeground", Color.WHITE); // White text
    
        // Create a dialog for editing the formation
        JTextField titleField = new JTextField(formation.getTitre());
        JTextField descriptionField = new JTextField(formation.getDescription());
        JTextField priceField = new JTextField(String.valueOf(formation.getPrix()));
        JTextField instructorField = new JTextField(formation.getFormateur());
    
        JPanel editPanel = new JPanel();
        editPanel.setLayout(new GridLayout(5, 2)); // Create a grid layout for form fields
        editPanel.setBackground(new Color(255, 248, 225)); // Creamy background color
    
        // Add form labels and fields
        editPanel.add(new JLabel("Title:"));
        editPanel.add(titleField);
        editPanel.add(new JLabel("Description:"));
        editPanel.add(descriptionField);
        editPanel.add(new JLabel("Price:"));
        editPanel.add(priceField);
        editPanel.add(new JLabel("Instructor:"));
        editPanel.add(instructorField);
    
        // Show dialog with the form
        int option = JOptionPane.showConfirmDialog(null, editPanel, "Edit Formation", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
    
        // Process user input if they clicked OK
        if (option == JOptionPane.OK_OPTION) {
            String newTitle = titleField.getText().trim();
            String newDescription = descriptionField.getText().trim();
            String priceText = priceField.getText().trim();
            String newInstructor = instructorField.getText().trim();
    
            // Input validation
            if (newTitle.isEmpty() || newDescription.isEmpty() || priceText.isEmpty() || newInstructor.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            try {
                // Parse and validate the price
                double newPrice = Double.parseDouble(priceText);
                if (newPrice < 0) {
                    JOptionPane.showMessageDialog(null, "Price cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                // Update the formation in the database
                ConnectionDBFormation db = new ConnectionDBFormation();
                boolean updated = db.updateFormation(formation.getId(), newTitle, newDescription, newPrice, newInstructor);
    
                // Provide feedback to the user
                if (updated) {
                    JOptionPane.showMessageDialog(null, "Formation updated successfully.");
                    frame.dispose(); // Close the frame
                    new FormateurPage(email); // Navigate to the updated page
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update formation.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for the price.", "Error", JOptionPane.ERROR_MESSAGE);
                showEditFormationDialog(formation, frame);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                showEditFormationDialog(formation, frame);
            }
        }
    }
    
    

    private JPanel createAccountPanel(String email,JFrame frame) {
        JPanel accountPanel = new JPanel();
        accountPanel.setLayout(null);
        accountPanel.setBackground(new Color(255, 248, 225)); // Creamy background color
    
        ConnectionDBUser dbUser = new ConnectionDBUser();
        Utilisateur user = dbUser.getUserFromDatabase(email); // Fetch user based on email
    
        if (user == null) {
            JOptionPane.showMessageDialog(accountPanel, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return accountPanel;
        }
    
        // Create and add "Username" label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 60, 120, 25);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        accountPanel.add(usernameLabel);
    
        JTextField usernameField = new JTextField(user.getNom());
        usernameField.setBounds(200, 60, 250, 25);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBackground(new Color(255, 255, 255)); // White background for text field
        usernameField.setEditable(false);
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(0x002147), 2)); // Blue border
        accountPanel.add(usernameField);
    
        // Create and add "Email" label and field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 100, 120, 25);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        accountPanel.add(emailLabel);
    
        JTextField emailField = new JTextField(user.getEmail());
        emailField.setBounds(200, 100, 250, 25);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setBackground(new Color(255, 255, 255)); // White background
        emailField.setEditable(false);
        emailField.setBorder(BorderFactory.createLineBorder(new Color(0x002147), 2)); // Blue border
        accountPanel.add(emailField);
    
        // Create and add "Password" label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 140, 120, 25);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        accountPanel.add(passwordLabel);
    
        JPasswordField passwordField = new JPasswordField(user.getMotDePasse());
        passwordField.setBounds(200, 140, 250, 25);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBackground(new Color(255, 255, 255)); // White background
        passwordField.setEditable(false);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(0x002147), 2)); // Blue border
        accountPanel.add(passwordField);
    
        // Show password checkbox
        JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setBounds(200, 170, 150, 25);
        showPasswordCheckBox.setFont(new Font("Arial", Font.PLAIN, 12));
        showPasswordCheckBox.setBackground(new Color(255, 248, 225)); // Creamy background for checkbox
        showPasswordCheckBox.setEnabled(false);
        accountPanel.add(showPasswordCheckBox);
        showPasswordCheckBox.addActionListener(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0); // Show password
            } else {
                passwordField.setEchoChar('*'); // Hide password
            }
        });
    
        // Save button (initially disabled)
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(150, 200, 100, 30);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBackground(new Color(0x002147)); // Navy blue background
        saveButton.setForeground(Color.WHITE); // White text color
        saveButton.setEnabled(false);
        accountPanel.add(saveButton);
    
        // Modify button
        JButton modifyButton = new JButton("Modify");
        modifyButton.setBounds(270, 200, 100, 30);
        modifyButton.setFont(new Font("Arial", Font.BOLD, 14));
        modifyButton.setBackground(new Color(0x002147)); // Navy blue background
        modifyButton.setForeground(Color.WHITE); // White text color
        accountPanel.add(modifyButton);
    
        // Delete button
        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(390, 200, 100, 30); // Placed to the right of Modify button
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setBackground(new Color(0x002147)); // Navy blue background
        deleteButton.setForeground(Color.WHITE); // White text color
        accountPanel.add(deleteButton);
    
        // Modify button action
        modifyButton.addActionListener(e -> {
            usernameField.setEditable(true);
            emailField.setEditable(true);
            passwordField.setEditable(true);
            showPasswordCheckBox.setEnabled(true);
            saveButton.setEnabled(true);
            modifyButton.setEnabled(false);
        });
    
        // Save button action
        saveButton.addActionListener(e -> {
            user.setNom(usernameField.getText());
            user.setEmail(emailField.getText());
            user.setMotDePasse(new String(passwordField.getPassword()));
    
            // Email format validation using a more robust regex
            String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            if (!emailField.getText().matches(emailPattern)) {
                JOptionPane.showMessageDialog(accountPanel, "Invalid email format. Please use 'email@example.com'.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Save the updated user data
            dbUser.updateUserInDatabase(user,email);
            JOptionPane.showMessageDialog(accountPanel, "Account details saved.");
            saveButton.setEnabled(false);
            modifyButton.setEnabled(true);
    
            // Disable editing again
            usernameField.setEditable(false);
            emailField.setEditable(false);
            passwordField.setEditable(false);
        });
    
        // Delete button action
        deleteButton.addActionListener(e -> {
            int confirmation = JOptionPane.showConfirmDialog(accountPanel,
                    "Are you sure you want to delete this user?", "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirmation == JOptionPane.YES_OPTION) {
                dbUser.deleteUserFromDatabase(email); // Assuming this method deletes the user
                JOptionPane.showMessageDialog(accountPanel, "User deleted successfully.");
                frame.dispose();
                new LoginPage();
            }
        });
    
        return accountPanel;
    }
    
    

    private JPanel createAjoutFormationPanel(JFrame frame) {
        JPanel ajoutFormationPanel = new JPanel();
        ajoutFormationPanel.setLayout(null);
        ajoutFormationPanel.setBackground(new Color(255, 248, 225)); // Creamy background
    
        // Create and position labels and text fields
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(50, 60, 100, 25);
        ajoutFormationPanel.add(titleLabel);
    
        JTextField titleField = new JTextField();
        titleField.setBounds(200, 60, 200, 25);
        ajoutFormationPanel.add(titleField);
    
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(50, 100, 100, 25);
        ajoutFormationPanel.add(descriptionLabel);
    
        JTextArea descriptionArea = new JTextArea(4, 20);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionScrollPane.setBounds(200, 100, 200, 100);
        ajoutFormationPanel.add(descriptionScrollPane);
    
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(50, 220, 100, 25);
        ajoutFormationPanel.add(priceLabel);
    
        JTextField priceField = new JTextField();
        priceField.setBounds(200, 220, 200, 25);
        ajoutFormationPanel.add(priceField);
    
        // Add the submit button with style
        JButton submitButton = new JButton("Add Formation");
        submitButton.setBounds(150, 270, 150, 30);
        submitButton.setBackground(new Color(0x002147)); // Dark blue background
        submitButton.setForeground(Color.WHITE); // White text
        ajoutFormationPanel.add(submitButton);
    
        submitButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String description = descriptionArea.getText().trim();
            String price = priceField.getText().trim();
        
            if (title.isEmpty() || description.isEmpty() || price.isEmpty()) {
                JOptionPane.showMessageDialog(ajoutFormationPanel, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                
                return;
            }
        
            try {
                ConnectionDBFormation dbFormation = new ConnectionDBFormation();
        
                // Check if a formation with the same title already exists
                if (dbFormation.formationExists(title)) {
                    JOptionPane.showMessageDialog(ajoutFormationPanel, "A formation with this title already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                    titleField.setText("");
                    return;
                }
        
                // Parse the price to ensure it is a valid number
                double priceValue = Double.parseDouble(price);
        
                if (priceValue < 0) {
                    JOptionPane.showMessageDialog(ajoutFormationPanel, "Price cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Add the new formation to the database
                dbFormation.addFormation(title, description, priceValue, email);
        
                // Show success message and proceed
                JOptionPane.showMessageDialog(ajoutFormationPanel, "Formation added successfully!");
                frame.dispose();
                new FormateurPage(email);
        
                // Clear the fields after submission
                titleField.setText("");
                descriptionArea.setText("");
                priceField.setText("");
            } catch (NumberFormatException ex) {
                // Handle invalid number format
                JOptionPane.showMessageDialog(ajoutFormationPanel, "Please enter a valid number for the price.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                // Handle other unexpected exceptions
                JOptionPane.showMessageDialog(ajoutFormationPanel, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        });
        
        
    
        return ajoutFormationPanel;
    }

    private void switchView(JPanel mainPanel, String cardName) {
        CardLayout layout = (CardLayout) mainPanel.getLayout();
        layout.show(mainPanel, cardName);
    }
}
