package Connections;

import Exceptions.UtilisateurNonTrouveException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Classes.Utilisateur;

public class ConnectionDBUser {
    private ConnectionDB connectionDB;
    
    public ConnectionDBUser() {
        connectionDB = new ConnectionDB(); // Instantiate ConnectionDB for database connections
    }

    // Method to check user credentials and throw exception if user doesn't exist
    public boolean checkCredentials(String username, String password) throws UtilisateurNonTrouveException {
        String query = "SELECT * FROM user WHERE email = ? AND password = ?"; // Assuming the table is named 'user'
        try (Connection conn = connectionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username); // Set username or email
            stmt.setString(2, password); // In practice, the password should be hashed for security reasons.

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new UtilisateurNonTrouveException("Invalid email or pasword");
            }
            return true; // If the result set contains data, the credentials are correct

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to check if a user exists
    public boolean userExists(String email) {
        String query = "SELECT COUNT(*) FROM user WHERE email = ?";
        try (Connection conn = connectionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // If the count is greater than 0, the user exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if an error occurs or no match found
    }

    // Method to register a new user
    public boolean registerUser(String username, String email, String password, String role) {
        if (userExists(email)) {
            return false; // User already exists
        }

        String query = "INSERT INTO user (username, email, password, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = connectionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password); // Password should be hashed in practice
            stmt.setString(4, role);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if the insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if an error occurs
    }

    // Method to get user information from the database based on the logged-in email
    public Utilisateur getUserFromDatabase(String email) {
        String query = "SELECT username, email, password, role FROM user WHERE email = ?"; // Assuming the table is
                                                                                           // 'user'
        try (Connection conn = connectionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email); // Set the email to the query parameter

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // If a user is found, return a Utilisateur object populated with the user's
                    // details
                    String username = rs.getString("username");
                    String userEmail = rs.getString("email");
                    String password = rs.getString("password");
                    // Create and return the Utilisateur object with the retrieved data
                    return new Utilisateur(username, userEmail, password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no user is found or if an error occurs
    }

    // Method to get the user's role based on their email
    public String getUserRole(String email) {
        String role = null;
        String query = "SELECT role FROM user WHERE email = ?"; // Assuming 'role' is stored in the 'user' table

        try (Connection conn = connectionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email); // Set the email to the query parameter

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    role = rs.getString("role"); // Retrieve the role from the result set
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return role; // Return the role (e.g., "Etudiant", "Formateur", etc.)
    }

    // Method to update user information in the database
    public void updateUserInDatabase(Utilisateur user,String email) {
        // SQL query to update user details
        String query = "UPDATE user SET username = ?, email = ?, password = ? WHERE email = ?";

        try (Connection conn = connectionDB.getConnection(); // Use the same connection instance
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getMotDePasse());
            stmt.setString(4, email); // Use email as the identifier

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "User updated successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "No user found with the provided email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Method to delete a user from the database based on their email
    public void deleteUserFromDatabase(String email) {
        String deleteQuery = "DELETE FROM user WHERE email = ?"; // Table name should match your database schema
        try (Connection conn = connectionDB.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery)) {
    
            preparedStatement.setString(1, email); // Set the email to delete
            int rowsAffected = preparedStatement.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("User with email '" + email + "' deleted successfully.");
                JOptionPane.showMessageDialog(null, "User deleted successfully.");
            } else {
                System.out.println("No user found with email '" + email + "'.");
                JOptionPane.showMessageDialog(null, "No user found with the provided email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting user: " + e.getMessage());
        }
    }
    
}
