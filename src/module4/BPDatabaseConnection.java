package module4;

import java.sql.Connection;
import java.sql.DriverManager;

public class BPDatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/healthsync";

    private static final String USER =
            "root";

    private static final String PASSWORD =
            "";

    public static Connection getConnection() {

        try {

            Connection conn =
                    DriverManager.getConnection(
                            URL,
                            USER,
                            PASSWORD);

            return conn;

        } catch (Exception e) {

            System.out.println(
                    "Database Connection Failed!");

            e.printStackTrace();

            return null;
        }
    }
}