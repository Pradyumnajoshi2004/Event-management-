package com.myapi.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Load PostgreSQL driver
        Class.forName("org.postgresql.Driver");
        
        // Get DATABASE_URL from environment
        String dbUrl = System.getenv("DATABASE_URL");
        
        if (dbUrl == null || dbUrl.isEmpty()) {
            throw new SQLException("DATABASE_URL environment variable is not set");
        }
        
        // Add jdbc: prefix if not present
        if (!dbUrl.startsWith("jdbc:")) {
            dbUrl = "jdbc:" + dbUrl;
        }
        
        // Add SSL requirement for external connection
        if (!dbUrl.contains("sslmode=")) {
            dbUrl += "?sslmode=require";
        }
        
        System.out.println("Connecting to PostgreSQL database...");
        
        try {
            Connection conn = DriverManager.getConnection(dbUrl);
            System.out.println("Database connected successfully!");
            return conn;
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            throw e;
        }
    }
}