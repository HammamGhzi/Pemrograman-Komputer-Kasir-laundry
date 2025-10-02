package laundry.ui;

import java.sql.*;

public class DatabaseConfig {
    // Konfigurasi Database untuk XAMPP/phpMyAdmin
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "laundrydb";
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?useSSL=false&serverTimezone=UTC";
    
    // Kredensial database default XAMPP
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // Default XAMPP tidak ada password
    
    // JDBC Driver
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    public static Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Koneksi database berhasil!");
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver tidak ditemukan!");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("Koneksi database gagal!");
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }
    
    public static boolean testConnection() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.close();
                return true;
            } catch (SQLException e) {
                return false;
            }
        }
        return false;
    }
    
    public static boolean validateLogin(String username, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            if (conn == null) return false;
            
            String sql = "SELECT id FROM users WHERE username = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            rs = pstmt.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            System.err.println("Error validasi login: " + e.getMessage());
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static boolean isDatabaseReady() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            if (conn == null) return false;
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SHOW TABLES LIKE 'users'");
            return rs.next();
            
        } catch (SQLException e) {
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}