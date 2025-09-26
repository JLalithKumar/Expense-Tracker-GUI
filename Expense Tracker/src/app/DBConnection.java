package app;
import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:expense.db";

    static {
        try {
            // Optional: explicitly load driver
            Class.forName("org.sqlite.JDBC");

            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement()) {
                String sql = "CREATE TABLE IF NOT EXISTS expenses (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "amount DOUBLE NOT NULL," +
                        "category TEXT NOT NULL," +
                        "date TEXT NOT NULL," +
                        "description TEXT)";
                stmt.execute(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
