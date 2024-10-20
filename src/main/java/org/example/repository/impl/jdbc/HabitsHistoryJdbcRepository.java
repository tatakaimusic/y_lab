package org.example.repository.impl.jdbc;

import org.example.model.HabitHistoryMark;
import org.example.repository.HabitHistoryRepository;
import org.example.sql.ConnectionFactory;
import org.example.sql.SqlHelper;
import org.example.util.DateHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HabitsHistoryJdbcRepository implements HabitHistoryRepository {

    private final SqlHelper sqlHelper;

    public HabitsHistoryJdbcRepository(ConnectionFactory connectionFactory) {
        this.sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void mark(Long habitId, LocalDate date) throws SQLException {
        Boolean mark = getLocalDateMark(habitId, date);
        if (mark == null) {
            create(habitId, date);
            mark = false;
        }
        Boolean finalMark = mark;
        sqlHelper.execute("" +
                "UPDATE habit_history_marks SET is_done = ? " +
                "WHERE habit_id = ? AND date = ?", ps -> {
            ps.setBoolean(1, !finalMark);
            ps.setLong(2, habitId);
            ps.setTimestamp(3, DateHelper.localDateToTimestamp(date));
            ps.executeUpdate();
            return null;
        });
    }

    @Override
    public void create(Long habitId, LocalDate date) throws SQLException {
        sqlHelper.execute("" +
                "INSERT INTO habit_history_marks (habit_id, date, is_done) " +
                "VALUES (?, ?, ?)", ps -> {
            ps.setLong(1, habitId);
            ps.setTimestamp(2, DateHelper.localDateToTimestamp(date));
            ps.setBoolean(3, false);
            ps.executeUpdate();
            return null;
        });
    }

    @Override
    public void delete(Long habitId) throws SQLException {
        sqlHelper.execute("" +
                "DELETE FROM habit_history_marks " +
                "WHERE habit_id = ?", ps -> {
            ps.setLong(1, habitId);
            ps.executeUpdate();
            return null;
        });
    }

    @Override
    public List<HabitHistoryMark> getHabitHistory(Long habitId) throws SQLException {
        List<HabitHistoryMark> habitHistory = new ArrayList<>();
        sqlHelper.execute("" +
                "SELECT * FROM habit_history_marks " +
                "WHERE habit_id = ? " +
                "ORDER BY date ASC ", ps -> {
            ps.setLong(1, habitId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                habitHistory.add(
                        new HabitHistoryMark(
                                rs.getLong("id"),
                                rs.getTimestamp("date").toLocalDateTime().toLocalDate(),
                                rs.getBoolean("is_done")
                        )
                );

            }
            return null;
        });
        return habitHistory;
    }

    @Override
    public Boolean getLocalDateMark(Long habitId, LocalDate date) throws SQLException {
        return sqlHelper.execute("" +
                "SELECT is_done FROM habit_history_marks " +
                "WHERE habit_id = ? AND date = ?", ps -> {
            ps.setLong(1, habitId);
            ps.setTimestamp(2, DateHelper.localDateToTimestamp(date));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("is_done");
            }
            return null;
        });
    }

    @Override
    public void clear() throws SQLException {
        sqlHelper.execute("" +
                "DELETE FROM habit_history_marks", ps -> {
            ps.executeUpdate();
            return null;
        });
    }
}
