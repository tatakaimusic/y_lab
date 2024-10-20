package org.example.sql;

import java.sql.*;

/**
 * Помошник для работы с sql.
 */
public class SqlHelper {

    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * Создание схем. Можно создать одну схему или несколько сразу.
     * @param url
     * @param username
     * @param password
     * @param schemas
     */
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

    /**
     * Выполнение sql-запроса с помощью PreparedStatement.
     * @param sql
     * @param executeSql
     * @return
     * @param <T>
     * @throws SQLException
     */
    public <T> T execute(String sql, ExecuteSql<T> executeSql) throws SQLException {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return executeSql.execute(ps);
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Получение подключения к базе данных.
     * @param url
     * @param username
     * @param password
     * @return
     */
    public static Connection getConnection(String url, String username, String password) {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
