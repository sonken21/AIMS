package com.aims.son.repositories.DbConnection;

import com.aims.son.utils.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class SQLiteConnectionManager {
    private static Logger LOGGER = Utils.getLogger(SQLiteConnectionManager.class.getName());
    private static final String connectionString = "jdbc:sqlite:src/main/resources/com/aims/son/assets/db/aims.db";
    private static Connection connection;

    public static Connection getConnection() {
        if (connection != null) return connection;
        try {
            connection = DriverManager.getConnection(connectionString);
            return connection;
        }
        catch (SQLException e) {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    public static void closeConnection() throws SQLException {
        if(connection != null) connection.close();
    }
}
