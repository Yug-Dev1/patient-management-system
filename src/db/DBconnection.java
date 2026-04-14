package db;
import java.sql.*;


public class DBconnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/stack_overflow_urgent_care";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    // DO NOT store a static connection - create fresh ones each time
    public static Connection getConnection() throws SQLException {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);  
            // Optional: Set connection properties
            conn.setAutoCommit(true);
          
            return conn;
            
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        } catch (SQLException e) {
            System.err.println("DB Connection Error: " + e.getMessage());
            throw e;
        }
    }
    
    // Optional: Test connection method
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Connection test failed: " + e.getMessage());
            return false;
        }
    }
}