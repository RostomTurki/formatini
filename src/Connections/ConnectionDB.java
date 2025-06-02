package Connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/formation";
    private static final String USER = "root";
    private static final String PASSWORD = "rostom/turki/mysql/root/047089/!";

    // Method to get the database connection
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
