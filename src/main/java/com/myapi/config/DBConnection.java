package com.myapi.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        
        // Get DATABASE_URL from Render environment
        String dbUrl = System.getenv("DATABASE_URL");
        
        if (dbUrl == null || dbUrl.isEmpty()) {
            // Fallback for local development with PostgreSQL
            dbUrl = "jdbc:postgresql://localhost:5432/event_api_db?ssl=false";
        }
        
        System.out.println("Connecting to PostgreSQL database...");
        return DriverManager.getConnection(dbUrl);
    }
}