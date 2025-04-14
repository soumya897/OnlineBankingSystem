import java.sql.*;

public class User {
    private Connection conn;

    public User() {
        try {
            conn = DBConnection.getConnection();
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }

    public boolean register(String username, String password) {
        try {
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Registration error: " + e.getMessage());
            return false;
        }
    }

    public boolean login(String username, String password) {
        try {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
            return false;
        }
    }

    public double getBalance(String username) throws SQLException {
        String query = "SELECT balance FROM users WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getDouble("balance");
        }
        return 0;
    }

    public void deposit(String username, double amount) throws SQLException {
        String query = "UPDATE users SET balance = balance + ? WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setDouble(1, amount);
        stmt.setString(2, username);
        stmt.executeUpdate();
    }

    public void withdraw(String username, double amount) throws SQLException {
        String query = "UPDATE users SET balance = balance - ? WHERE username = ? AND balance >= ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setDouble(1, amount);
        stmt.setString(2, username);
        stmt.setDouble(3, amount);
        int updated = stmt.executeUpdate();
        if (updated == 0) {
            throw new SQLException("Insufficient funds.");
        }
    }
}
