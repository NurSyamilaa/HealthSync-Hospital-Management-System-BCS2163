package module2;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConfig {
    // Sambungan URL rasmi ke MySQL XAMPP Localhost (Port 3306)
    private static final String URL = "jdbc:mysql://localhost:3306/healthsync";
    private static final String USER = "root"; // Default username XAMPP
    private static final String PASSWORD = ""; // Default password XAMPP adalah kosong

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initializeDatabase() {
        // Pengesahan sambungan aktif ke server XAMPP
        try (Connection conn = getConnection()) {
            System.out.println("System Confirmation: Connected to XAMPP MySQL database successfully!");
        } catch (Exception e) {
            System.err.println("Database Connection Error: " + e.getMessage());
        }
    }
}