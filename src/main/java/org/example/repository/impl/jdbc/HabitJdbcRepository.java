package org.example.repository.impl.jdbc;

import org.example.model.Habit;
import org.example.model.HabitPeriod;
import org.example.repository.HabitRepository;
import org.example.sql.ConnectionFactory;
import org.example.sql.SqlHelper;
import org.example.util.DateHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HabitJdbcRepository implements HabitRepository {

    private final SqlHelper sqlHelper;

    public HabitJdbcRepository(ConnectionFactory connectionFactory) {
        this.sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public Habit create(Long userId, Habit habit, LocalDate createDate) throws SQLException {
        sqlHelper.execute("" +
                "INSERT INTO habits(user_id, title, description, period, create_date)" +
                "VALUES (?, ?, ?, ?, ?)", ps -> {
            ps.setLong(1, userId);
            ps.setString(2, habit.getTitle());
            ps.setString(3, habit.getDescription());
            ps.setString(4, habit.getPeriod().toString());
            ps.setTimestamp(5, DateHelper.localDateToTimestamp(createDate));
            ps.executeUpdate();
            return null;
        });
        return get(userId, habit.getTitle()).orElse(null);
    }

    @Override
    public Optional<Habit> get(Long userId, String habitTitle) throws SQLException {
        return Optional.ofNullable(sqlHelper.execute("" +
                "SELECT * FROM habits " +
                "WHERE user_id = ? AND title = ?", ps -> {
            ps.setLong(1, userId);
            ps.setString(2, habitTitle);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return createNewHabit(rs);
        }));
    }

    @Override
    public List<Habit> getAllHabitsByUserId(Long userId) throws SQLException {
        List<Habit> habits = new ArrayList<>();
        sqlHelper.execute("" +
                "SELECT * FROM habits " +
                "WHERE user_id = ?", ps -> {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                habits.add(
                        createNewHabit(rs)
                );
            }
            return null;
        });
        return habits;
    }

    @Override
    public List<Habit> getAllHabits() throws SQLException {
        List<Habit> habits = new ArrayList<>();
        sqlHelper.execute("" +
                "SELECT * FROM habits ", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                habits.add(
                        createNewHabit(rs)
                );
            }
            return null;
        });
        return habits;
    }

    @Override
    public void update(Long userId, Habit habit) throws SQLException {
        sqlHelper.execute("" +
                "UPDATE habits SET title = ?, description = ?, period = ? " +
                "WHERE id = ?", ps -> {
            ps.setString(1, habit.getTitle());
            ps.setString(2, habit.getDescription());
            ps.setString(3, habit.getPeriod().toString());
            ps.setLong(4, habit.getId());
            return ps.executeUpdate();
        });
    }

    @Override
    public void delete(Long userId, String habitTitle) throws SQLException {
        sqlHelper.execute("" +
                "DELETE FROM habits WHERE user_id = ? AND title = ?", ps -> {
            ps.setLong(1, userId);
            ps.setString(2, habitTitle);
            return ps.executeUpdate();
        });
    }

    @Override
    public void clear() throws SQLException {
        sqlHelper.execute("" +
                "DELETE FROM habits", ps -> {
            ps.executeUpdate();
            return null;
        });
    }

    private Habit createNewHabit(ResultSet rs) throws SQLException {
        return new Habit(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("description"),
                HabitPeriod.valueOf(rs.getString("period")),
                rs.getTimestamp("create_date").toLocalDateTime().toLocalDate()
        );
    }

}
