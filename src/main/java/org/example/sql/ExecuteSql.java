package org.example.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ExecuteSql<T> {

    T execute(PreparedStatement ps) throws SQLException;
}
