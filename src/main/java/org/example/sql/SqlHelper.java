package org.example.sql;

import org.postgresql.core.ConnectionFactory;

import java.sql.*;

public class SqlHelper {

    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public static void createSchema(String url, String username, String password, String... schemas) {
        try (Connection connection = getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            for (String schema : schemas) {
                statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS " + schema);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(String url, String username, String password) {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
