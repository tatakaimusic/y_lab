package org.example.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Интерфейс для получения подключения к базе данных.
 */
public interface ConnectionFactory {

    /**
     * Получение подключения.
     * @return
     * @throws SQLException
     */
    Connection getConnection() throws SQLException;
}
