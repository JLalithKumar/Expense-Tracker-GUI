package app;
import java.sql.*;
import java.util.*;

public class ExpenseDAO {

    public static void addExpense(Expense e) throws SQLException {
        String sql = "INSERT INTO expenses(amount, category, date, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, e.getAmount());
            ps.setString(2, e.getCategory());
            ps.setString(3, e.getDate());
            ps.setString(4, e.getDescription());
            ps.executeUpdate();
        }
    }

    public static List<Expense> getAllExpenses() throws SQLException {
        List<Expense> list = new ArrayList<>();
        String sql = "SELECT * FROM expenses";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Expense(
                        rs.getInt("id"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        rs.getString("date"),
                        rs.getString("description")
                ));
            }
        }
        return list;
    }

    public static void deleteExpense(int id) throws SQLException {
        String sql = "DELETE FROM expenses WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public static void updateExpense(Expense e) throws SQLException {
        String sql = "UPDATE expenses SET amount=?, category=?, date=?, description=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, e.getAmount());
            ps.setString(2, e.getCategory());
            ps.setString(3, e.getDate());
            ps.setString(4, e.getDescription());
            ps.setInt(5, e.getId());
            ps.executeUpdate();
        }
    }

    public static Map<String, Double> getCategoryTotals() throws SQLException {
        Map<String, Double> map = new HashMap<>();
        String sql = "SELECT category, SUM(amount) AS total FROM expenses GROUP BY category";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                map.put(rs.getString("category"), rs.getDouble("total"));
            }
        }
        return map;
    }
}
