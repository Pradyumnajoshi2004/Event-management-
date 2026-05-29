package com.myapi.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        
        String dbUrl = System.getenv("DATABASE_URL");
        
        if (dbUrl == null || dbUrl.isEmpty()) {
            throw new SQLException("DATABASE_URL environment variable is not set");
        }
        
        // Clean up the URL if it already has jdbc: prefix
        if (!dbUrl.startsWith("jdbc:")) {
            dbUrl = "jdbc:" + dbUrl;
        }
        
        // Remove any duplicate host/port patterns if present
        // This handles the case where the URL has been concatenated multiple times
        if (dbUrl.contains(".render.com:5432/") && dbUrl.indexOf(".render.com:5432/") != dbUrl.lastIndexOf(".render.com:5432/")) {
            // Get the last occurrence of the database name
            String lastPart = dbUrl.substring(dbUrl.lastIndexOf("/event_db"));
            String firstPart = dbUrl.substring(0, dbUrl.indexOf(".render.com:5432/") + ".render.com:5432/".length());
            dbUrl = firstPart + lastPart;
        }
        
        System.out.println("Connecting to PostgreSQL database...");
        System.out.println("URL: " + dbUrl); // Debug line - remove in production
        
        return DriverManager.getConnection(dbUrl);
    }
}