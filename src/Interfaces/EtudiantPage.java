package Interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.Locale;

import Classes.Utilisateur;
import Classes.Formation;
// import Connections.ConnectionDB;
import Connections.ConnectionDBFormation;
import Connections.ConnectionDBUser;
import Exceptions.FormationDejaInscriteException;

public class EtudiantPage {

    private String email; // Instance variable to store the email

    public EtudiantPage(String email) {
        this.email = email; // Store email in the instance variable
        ConnectionDBUser dbUser = new ConnectionDBUser();
        // Create the main frame
        JFrame frame = new JFrame("Interface Etudiant");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Larger frame size
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(255, 248, 225)); // Set creamy background

        // Create the welcome panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center-align the text
        welcomePanel.setBackground(new Color(250, 250, 250)); // Navy blue background

        JLabel welcomeLabel = new JLabel("Welcome " + dbUser.getUserFromDatabase(email).getNom() + " to Formatini");
        welcomeLabel.setForeground(Color.DARK_GRAY); // White text color
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Bold, larger font

        welcomePanel.add(welcomeLabel);
        // Create the navigation panel (left-side bar)
        JPanel navBar = new JPanel();
        navBar.setLayout(new BorderLayout());
        navBar.setPreferredSize(new Dimension(200, frame.getHeight()));
        navBar.setBackground(new Color(255, 248, 225)); // Creamy background
        navBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(0x002147))); // Navy blue border

        // Buttons on the nav bar
        JPanel topButtons = new JPanel();
        topButtons.setLayout(new GridLayout(5, 1, 10, 0)); // Added one more row for Add Formation button
        topButtons.setBackground(new Color(255, 248, 225)); // Creamy background

        JButton homeButton = new JButton("Home");
        homeButton.setBackground(new Color(0x002147)); // Navy blue background
        homeButton.setForeground(Color.WHITE); // White text color

        JButton formationsButton = new JButton("My Subscriptions");
        formationsButton.setBackground(new Color(0x002147)); // Navy blue background
        formationsButton.setForeground(Color.WHITE); // White text color

        JButton accountButton = new JButton("Account");
        accountButton.setBackground(new Color(0x002147)); // Navy blue background
        accountButton.setForeground(Color.WHITE); // White text color

        topButtons.add(homeButton);
        topButtons.add(formationsButton);
        topButtons.add(accountButton);

        // Log out button at the bottom
        JButton logoutButton = new JButton("Log Out");
        logoutButton.setBackground(new Color(0x002147)); // Navy blue background
        logoutButton.setForeground(Color.WHITE); // White text color

        // Add buttons to navBar
        navBar.add(topButtons, BorderLayout.NORTH);
        navBar.add(logoutButton, BorderLayout.SOUTH);

        // Create the main panel with CardLayout
        JPanel mainPanel = new JPanel(new CardLayout());

        // Create individual panels for each view
        JScrollPane homePanel = createHomePanel(frame);
        JScrollPane formationsPanel = createSubscribedPanel(email, frame);
        JPanel accountPanel = createAccountPanel(email, frame);

        // Add panels to the mainPanel with card names
        mainPanel.add(homePanel, "Home");
        mainPanel.add(formationsPanel, "Formations");
        mainPanel.add(accountPanel, "Account");

        // Add action listeners to buttons to switch views
        homeButton.addActionListener(e -> switchView(mainPanel, "Home"));
        formationsButton.addActionListener(e -> switchView(mainPanel, "Formations"));
        accountButton.addActionListener(e -> switchView(mainPanel, "Account"));

        logoutButton.addActionListener(e -> {
            // Set the locale to English to display Yes/No in English
            Locale.setDefault(Locale.ENGLISH);

            int confirmed = JOptionPane.showConfirmDialog(frame, "Are you sure you want to log out?", "Log Out",
                    JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frame, "You have logged out.");
                frame.dispose(); // Close the current frame

                // Create and show the login page after logging out
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

    private JScrollPane createHomePanel(JFrame frame) {
        // Main panel that will hold all formation panels vertically
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS)); // Vertical stacking
        homePanel.setBackground(new Color(255, 248, 225)); // Creamy background for home panel

        // Fetch formations from the database
        ConnectionDBFormation db = new ConnectionDBFormation();
        List<Formation> formations = db.getAllFormations();

        // Add each formation to the home panel
        for (Formation formation : formations) {
            JPanel formationPanel = createFormationPanel(formation, frame);
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

    private JPanel createFormationPanel(Formation formation, JFrame frame) {
        // Create the panel with a fixed height
        JPanel formationPanel = new JPanel();
        formationPanel.setLayout(null); // Manual positioning
        formationPanel.setPreferredSize(new Dimension(0, 80)); // Initial placeholder width
        formationPanel.setBackground(new Color(252, 252, 252)); // Creamy background for the panel
        formationPanel.setBorder(BorderFactory.createLineBorder(new Color(0x002147), 2)); // Navy blue border

        // Title label
        JLabel titleLabel = new JLabel("Title: " + formation.getTitre());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formationPanel.add(titleLabel);

        // Description label
        JLabel descriptionLabel = new JLabel("Description: " + formation.getDescription());
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        formationPanel.add(descriptionLabel);

        // Price label
        JLabel priceLabel = new JLabel("Price: " + formation.getPrix());
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        formationPanel.add(priceLabel);

        // Instructor label
        JLabel instructorLabel = new JLabel("Instructor: " + formation.getFormateur());
        instructorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        formationPanel.add(instructorLabel);

        // Add button
        JButton addButton = new JButton("Add");
        addButton.setFont(new Font("Arial", Font.BOLD, 12));
        addButton.setBackground(new Color(0x002147)); // Navy blue background
        addButton.setForeground(Color.WHITE); // White text color

        addButton.addActionListener(e -> {
            // Check if the user has already added the formation
            ConnectionDBFormation dbFormation = new ConnectionDBFormation();
            List<Formation> userFormations = dbFormation.getFormationsForUser(email);

            boolean alreadyAdded = false;
            for (Formation userFormation : userFormations) {
                if (userFormation.getId() == formation.getId()) {
                    alreadyAdded = true;
                    break;
                }
            }

            try {
                if (alreadyAdded) {
                    // Throw FormationDejaInscriteException if already subscribed
                    throw new FormationDejaInscriteException("You have already added this formation.");
                } else {
                    // Add the user to the formation
                    int formationId = formation.getId();
                    dbFormation.addFormationToUser(email, formationId); // Add formation to user in DB
                    JOptionPane.showMessageDialog(formationPanel,
                            "You have successfully added the formation: " + formation.getTitre());
                    frame.dispose();
                    new EtudiantPage(email);
                }
            } catch (FormationDejaInscriteException ex) {
                // Show message if FormationDejaInscriteException is thrown
                JOptionPane.showMessageDialog(formationPanel, ex.getMessage());
            }
        });

        formationPanel.add(addButton);

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
                int buttonHeight = 30;

                // Set bounds for labels
                titleLabel.setBounds(padding, padding, dynamicWidth - 2 * padding - buttonWidth, labelHeight);
                descriptionLabel.setBounds(padding, padding + 20, dynamicWidth - 2 * padding - buttonWidth,
                        labelHeight);
                priceLabel.setBounds(padding, padding + 40, dynamicWidth - 2 * padding - buttonWidth, labelHeight);
                instructorLabel.setBounds(padding, padding + 60, dynamicWidth - 2 * padding - buttonWidth, labelHeight);

                // Set bounds for button (right side, vertically centered)
                addButton.setBounds(dynamicWidth - buttonWidth - padding, (80 - buttonHeight) / 2, buttonWidth,
                        buttonHeight);
            }
        });

        return formationPanel;
    }

    private JPanel createInscriptionPanel(Formation formation, JFrame frame) {
        // Create the panel with a fixed height
        JPanel inscriptionPanel = new JPanel();
        inscriptionPanel.setLayout(null); // Manual positioning
        inscriptionPanel.setPreferredSize(new Dimension(0, 80)); // Initial placeholder width
        inscriptionPanel.setBackground(new Color(255, 248, 225)); // Creamy background for the panel
        inscriptionPanel.setBorder(BorderFactory.createLineBorder(new Color(0x002147), 2)); // Navy blue border

        // Title label
        JLabel titleLabel = new JLabel("Title: " + formation.getTitre());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inscriptionPanel.add(titleLabel);

        // Description label
        JLabel descriptionLabel = new JLabel("Description: " + formation.getDescription());
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        inscriptionPanel.add(descriptionLabel);

        // Price label
        JLabel priceLabel = new JLabel("Price: " + formation.getPrix());
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        inscriptionPanel.add(priceLabel);

        // Instructor label
        JLabel instructorLabel = new JLabel("Instructor: " + formation.getFormateur());
        instructorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        inscriptionPanel.add(instructorLabel);

        // Delete button
        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 12));
        deleteButton.setBackground(new Color(0x002147)); // Navy blue background
        deleteButton.setForeground(Color.WHITE); // White text color
        deleteButton.addActionListener(e -> {
            // Check if the user is already subscribed to the formation
            ConnectionDBFormation dbFormation = new ConnectionDBFormation();
            List<Formation> userFormations = dbFormation.getFormationsForUser(email);

            boolean isSubscribed = false;
            for (Formation userFormation : userFormations) {
                if (userFormation.getId() == formation.getId()) {
                    isSubscribed = true;
                    break;
                }
            }

            if (isSubscribed) {
                // Remove the formation from the user's list
                dbFormation.removeFormationFromUser(email, formation.getId());
                JOptionPane.showMessageDialog(inscriptionPanel,
                        "You have successfully removed the formation: " + formation.getTitre());
                frame.dispose();
                new EtudiantPage(email);
            } else {
                // Show message if the user is not subscribed to the formation
                JOptionPane.showMessageDialog(inscriptionPanel, "You are not subscribed to this formation.");
            }
        });
        inscriptionPanel.add(deleteButton);

        // Dynamically adjust component positions based on panel size
        inscriptionPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int panelWidth = inscriptionPanel.getParent().getWidth();
                int dynamicWidth = (int) (panelWidth * 0.9); // Set width to 90% of parent width
                inscriptionPanel.setPreferredSize(new Dimension(dynamicWidth, 100));

                int padding = 10;
                int labelHeight = 20;
                int buttonWidth = 80;
                int buttonHeight = 30;

                // Set bounds for labels
                titleLabel.setBounds(padding, padding, dynamicWidth - 2 * padding - buttonWidth, labelHeight);
                descriptionLabel.setBounds(padding, padding + 20, dynamicWidth - 2 * padding - buttonWidth,
                        labelHeight);
                priceLabel.setBounds(padding, padding + 40, dynamicWidth - 2 * padding - buttonWidth, labelHeight);
                instructorLabel.setBounds(padding, padding + 60, dynamicWidth - 2 * padding - buttonWidth, labelHeight);

                // Set bounds for button (right side, vertically centered)
                deleteButton.setBounds(dynamicWidth - buttonWidth - padding, (80 - buttonHeight) / 2, buttonWidth,
                        buttonHeight);
            }
        });

        return inscriptionPanel;
    }

    private void switchView(JPanel mainPanel, String cardName) {
        CardLayout layout = (CardLayout) mainPanel.getLayout();
        layout.show(mainPanel, cardName);
    }

    private JPanel createAccountPanel(String email, JFrame frame) {
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
            dbUser.updateUserInDatabase(user, email);
            JOptionPane.showMessageDialog(accountPanel, "Account details saved.");
            saveButton.setEnabled(false);
            modifyButton.setEnabled(true);
            setEmail(emailField.getText());
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

    private JScrollPane createSubscribedPanel(String email, JFrame frame) {
        // Fetch the formations the user is subscribed to
        ConnectionDBFormation dbSubscribed = new ConnectionDBFormation();
        List<Formation> userFormations = dbSubscribed.getFormationsForUser(email);

        JPanel formationsPanel = new JPanel();
        formationsPanel.setLayout(new BoxLayout(formationsPanel, BoxLayout.Y_AXIS));
        formationsPanel.setBackground(new Color(255, 248, 225)); // Creamy background for the main panel

        if (userFormations.isEmpty()) {
            JLabel noFormationsLabel = new JLabel("You are not subscribed to any formations.");
            noFormationsLabel.setFont(new Font("Arial", Font.BOLD, 16));
            noFormationsLabel.setForeground(Color.BLACK); // Set label text to black
            formationsPanel.add(noFormationsLabel);
        } else {
            for (Formation formation : userFormations) {
                // Create a panel for each subscribed formation
                JPanel inscriptionPanel = createInscriptionPanel(formation, frame); // Use createInscriptionPanel for
                                                                                    // each
                // formation
                inscriptionPanel.setBackground(new Color(252, 252, 252)); // Set each inscription panel to creamy
                                                                          // background
                inscriptionPanel.setBorder(BorderFactory.createLineBorder(new Color(0x002147), 2)); // Blue border
                                                                                                    // around each panel
                formationsPanel.add(inscriptionPanel);
                formationsPanel.add(Box.createVerticalStrut(10)); // Add spacing between formations
            }
        }

        JScrollPane scrollPane = new JScrollPane(formationsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return scrollPane;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
