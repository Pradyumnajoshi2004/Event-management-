package com.myapi.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    // XAMPP MySQL connection settings
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "event_api_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + 
                                      "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}