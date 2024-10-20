package org.example.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Интерфейс для выполнения sql-запроса.
 * @param <T>
 */
public interface ExecuteSql<T> {


    /**
     * Выполнение запроса.
     * @param ps
     * @return
     * @throws SQLException
     */
    T execute(PreparedStatement ps) throws SQLException;
}
