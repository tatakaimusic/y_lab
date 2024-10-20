package org.example.service;

import org.example.model.Habit;
import org.example.model.HabitPeriod;
import org.example.model.Order;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Интрфейс сервиса привычек.
 */
public interface HabitService {

    /**
     * Создает привычку по userId и экзмпляру Habit.
     * @param userId
     * @param habit
     * @return
     */
    Habit create(Long userId, Habit habit) throws SQLException;

    /**
     * Создает привычку по userId, экзмпляру Habit и дате.
     * @param userId
     * @param habit
     * @return
     */
    Habit create(Long userId, Habit habit, LocalDate date) throws SQLException;

    /**
     * Получение привычки по userId и названию привычки.
     * Вернет пустой Optional, если значения нет.
     * @param userId
     * @param habitTitle
     * @return
     */
    Habit get(Long userId, String habitTitle) throws SQLException;

    /**
     * Вернет список всех привычек для пользовтеля с userId.
     * Вернет пустой list, если привычек еще нет.
     * @param userId
     * @return
     */
    List<Habit> getAllHabitsByUserId(Long userId) throws SQLException;

    /**
     * Вернет список всех привычек для пользовтеля с userId в указанном порядке.
     * Вернет пустой list, если привычек еще нет.
     * @param userId
     * @return
     */
    List<Habit> getAllHabitsByUserIdOrderedByDate(Long userId, Order order) throws SQLException;

    /**
     * Вернет список всех привычек с указанным period для пользовтеля с userId.
     * Вернет пустой list, если привычек еще нет.
     * @param userId
     * @return
     */
    List<Habit> getAllHabitsByUserIdAndPeriod(Long userId, HabitPeriod period) throws SQLException;

    /**
     * Вернет все привычки в хранилизе.
     * Вернет пустой list, если их еще нет.
     * @return
     */
    List<Habit> getAllHabits() throws SQLException;

    /**
     *  Обновит привычку по старому названию.
     * @param userId
     * @param oldHabitTitle
     * @param habit
     */
    void update(Long userId, String oldHabitTitle, Habit habit) throws SQLException;

    /**
     * Удалит привычку по userId и названию привычки.
     * @param userId
     * @param habitTitle
     */
    void delete(Long userId, String habitTitle, Long habitId) throws SQLException;

}
