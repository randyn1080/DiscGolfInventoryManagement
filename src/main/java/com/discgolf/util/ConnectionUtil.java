package com.discgolf.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionUtil.class);
    private static Connection connection;

    // prevents instantiation
    private ConnectionUtil() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // load db props from database.properties
                Properties props = new Properties();
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                InputStream input = loader.getResourceAsStream("database.properties");
                props.load(input);

                String url = props.getProperty("jdbc.url");
                String username = props.getProperty("jdbc.username");
                String password = props.getProperty("jdbc.password");

                // create connection
                connection = DriverManager.getConnection(url, username, password);
                logger.info("Connection established successfully");
            } catch (Exception e) {
                logger.error("Error connecting to the database", e);
                throw new RuntimeException("Error connecting to the database", e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                logger.info("Database connection closed");
            } catch (SQLException e) {
                logger.error("Error closing database connection", e);
            }
        }
    }
}
