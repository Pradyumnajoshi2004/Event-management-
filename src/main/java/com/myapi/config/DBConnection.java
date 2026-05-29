package com.myapi.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Explicitly load the PostgreSQL driver
        Class.forName("org.postgresql.Driver");
        
        // Get DATABASE_URL from Render environment
        String dbUrl = System.getenv("DATABASE_URL");
        
        if (dbUrl == null || dbUrl.isEmpty()) {
            throw new SQLException("DATABASE_URL environment variable is not set");
        }
        
        // 1. Convert "postgres://" to "jdbc:postgresql://" safely
        if (dbUrl.startsWith("postgres://")) {
            dbUrl = dbUrl.replace("postgres://", "jdbc:postgresql://");
        } else if (!dbUrl.startsWith("jdbc:postgresql://")) {
            // Backup catch-all if it starts with nothing or standard jdbc
            if (!dbUrl.startsWith("jdbc:")) {
                dbUrl = "jdbc:" + dbUrl;
            }
        }
        
        // 2. Add SSL parameter properly depending on existing queries
        if (!dbUrl.contains("ssl=") && !dbUrl.contains("sslmode=")) {
            if (dbUrl.contains("?")) {
                dbUrl += "&ssl=true";
            } else {
                dbUrl += "?ssl=true";
            }
        }
        
        System.out.println("Connecting to PostgreSQL database...");
        
        try {
            // DriverManager will parse the username and password directly out of this URL format
            Connection conn = DriverManager.getConnection(dbUrl);
            System.out.println("Database connected successfully!");
            return conn;
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            throw e;
        }
    }
}