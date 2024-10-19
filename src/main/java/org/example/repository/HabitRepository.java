package org.example.repository;

import org.example.model.Habit;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Хранилище привычек пользовтеля.
 */
public interface HabitRepository {

    /**
     * Создает привычку по userId, экзмпляру Habit и дате.
     * Id назначается с помощью инкрементации counter.
     * @param userId
     * @param habit
     * @return
     */
    Habit create(Long userId, Habit habit, LocalDate createDate) throws SQLException;

    /**
     * Получение привычки по userId и названию привычки.
     * Вернет пустой Optional, если значения нет.
     * @param userId
     * @param habitTitle
     * @return
     */
    Optional<Habit> get(Long userId, String habitTitle) throws SQLException;

    /**
     * Вернет список всех привычек для пользовтеля с userId.
     * Вернет пустой list, если привычек еще нет.
     * @param userId
     * @return
     */
    List<Habit> getAllHabitsByUserId(Long userId) throws SQLException;

    /**
     * Вернет все привычки в хранилизе.
     * Вернет пустой list, если их еще нет.
     * @return
     */
    List<Habit> getAllHabits() throws SQLException;

    /**
     *  Обновит привычку по userId.
     * @param userId
     * @param habit
     */
    void update(Long userId, Habit habit) throws SQLException;

    /**
     * Удалит привычку по userId и названию привычки.
     * @param userId
     * @param habitTitle
     */
    void delete(Long userId, String habitTitle) throws SQLException;

    /**
     * Отчистит хранилище. Используется для удобного тестирования.
     */
    void clear() throws SQLException;

}
