package org.example.repository.impl.jdbc;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.sql.ConnectionFactory;
import org.example.sql.SqlHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserJdbcRepository implements UserRepository {

    private final SqlHelper sqlHelper;


    public UserJdbcRepository(ConnectionFactory connectionFactory) {
        this.sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public Optional<User> create(User user) throws SQLException {
        sqlHelper.execute("" +
                "INSERT INTO users(name, email, password)" +
                "VALUES (?, ?, ?)", ps -> {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();
            return null;
        });
        return get(user.getEmail());
    }

    @Override
    public User update(User user) throws SQLException {
        sqlHelper.execute("" +
                "UPDATE users SET name = ?, email = ?, password = ? " +
                "WHERE id = ?", ps -> {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setLong(4, user.getId());
            return ps.executeUpdate();
        });
        return get(user.getEmail()).orElse(null);
    }

    @Override
    public void delete(User user) throws SQLException {
        sqlHelper.execute("" +
                "DELETE FROM users WHERE id = ?", ps -> {
            ps.setLong(1, user.getId());
            return ps.executeUpdate();
        });
    }

    @Override
    public Optional<User> get(String email) throws SQLException {
        return Optional.ofNullable(sqlHelper.execute("" +
                "SELECT * FROM users " +
                "WHERE email = ?", ps -> {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new User(
                    rs.getLong("id"),
                    rs.getString("name"),
                    email,
                    rs.getString("password")
            );
        }));
    }

}
