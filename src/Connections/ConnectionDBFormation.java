package Connections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Classes.Formation;

public class ConnectionDBFormation {
    private ConnectionDB connectionDB;

    public ConnectionDBFormation() {
        connectionDB = new ConnectionDB(); // Instantiate ConnectionDB for database connections
    }

    // Method to add a new formation
    public boolean addFormation(String title, String description, double prix, String formateur) {
        String query = "INSERT INTO formations (titre, description, prix, formateur) VALUES (?, ?, ?, ?)";
        try (Connection conn = connectionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setDouble(3, prix);
            stmt.setString(4, formateur);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if the insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to check if a formation exists by title
    public boolean formationExists(String title) {
        String query = "SELECT COUNT(*) FROM formations WHERE titre = ?";
        try (Connection conn = connectionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, title);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Formation exists if count > 0
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to retrieve all formations
    public List<Formation> getAllFormations() {
        String query = "SELECT * FROM formations";
        List<Formation> formations = new ArrayList<>();
        try (Connection conn = connectionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("idformations"); // Get auto-incremented ID
                String title = rs.getString("titre");
                String description = rs.getString("description");
                Double price = rs.getDouble("prix");
                String instructor = rs.getString("formateur");

                // Create a Formation object and add it to the list
                Formation formation = new Formation(id, title, description, price, instructor);
                formations.add(formation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return formations;
    }

    public boolean updateFormation(int id, String title, String description, double price, String instructor) {
        String query = "UPDATE formations SET titre = ?, description = ?, prix = ?, formateur = ? WHERE idformations = ?";
        try (Connection conn = connectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setDouble(3, price);
            stmt.setString(4, instructor);
            stmt.setInt(5, id);
    
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Method to delete a formation by title
    public boolean deleteFormation(int idformations) {
        String query = "DELETE FROM formations WHERE idformations = ?";
        try (Connection conn = connectionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idformations);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if a row was deleted
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean addFormation(String title, String description, Double prix, String formateur) {
        if (formationExists(title)) {
            System.out.println("Formation with this title already exists.");
            return false; // Formation title is already taken
        }
        String query = "INSERT INTO formations (titre, description, prix, formateur) VALUES (?, ?, ?, ?)";
        try (Connection conn = connectionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setDouble(3, prix);
            stmt.setString(4, formateur);
    
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Method to get formations for a specific user based on email
    public List<Formation> getFormationsForUser(String userId) {
        List<Formation> formations = new ArrayList<>();
        String query = "SELECT f.* FROM formations f "
                + "INNER JOIN inscriptions i ON f.idformations = i.idformations "
                + "WHERE i.iduser = ?"; // Changed to use 'iduser' instead of 'email'

        try (Connection conn = connectionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userId); // Use the correct user ID (assuming userId is passed)
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Formation formation = new Formation(
                        rs.getInt("idformations"), // Assuming idformations is the column name
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getDouble("prix"),
                        rs.getString("formateur")

                );
                formations.add(formation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return formations;
    }
    public List<Formation> getFormationsByFormateur(String email) {
        List<Formation> formations = new ArrayList<>();
        String query = "SELECT * FROM formations WHERE formateur = ?"; // Query to get formations by formateur (email)
    
        try (Connection conn = connectionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setString(1, email); // Set the email (formateur) parameter
    
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Create Formation objects and add them to the list
                    Formation formation = new Formation(
                            rs.getInt("idformations"),
                            rs.getString("titre"),
                            rs.getString("description"),
                            rs.getDouble("prix"),
                            rs.getString("formateur")
                    );
                    formations.add(formation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return formations;
    }
    

    // Method to add a user to a formation
    public void addFormationToUser(String userEmail, int formationId) {
        String query = "INSERT INTO inscriptions (iduser, idformations) VALUES (?, ?)";

        try (Connection connection = connectionDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userEmail); // Set the user's email
            preparedStatement.setInt(2, formationId); // Set the formation ID

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User successfully added to the formation.");
            } else {
                System.out.println("Failed to add user to the formation.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to remove a user from a formation
    public boolean removeFormationFromUser(String userEmail, int formationId) {
        String query = "DELETE FROM inscriptions WHERE iduser = ? AND idformations = ?";
        try (Connection conn = connectionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userEmail); // Set the user's email
            stmt.setInt(2, formationId); // Set the formation ID

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if a row was deleted
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
}
