package org.example.repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

/**
 * Интерфейс для хранения и обработки истории выолнения привычек.
 */
public interface HabitHistoryRepository {

    /**
     * Меня значение выполненности привычки за указнную дату по habitId на противоположное.
     * @param habitId
     * @param date
     */
    void mark(Long habitId, LocalDate date) throws SQLException;

    /**
     * Доавбляет историю указанный день по habitId.
     * @param habitId
     * @param date
     */
    void create(Long habitId, LocalDate date) throws SQLException;

    /**
     * Удаляет историю привычки по habitId.
     * @param habitId
     */
    void delete(Long habitId) throws SQLException;

    /**
     * Достает историю привычки по habitId.
     * Вернет null, если у привычки нет истории.
     * @param habitId
     * @return
     */
    Map<LocalDate, Boolean> getHabitHistory(Long habitId) throws SQLException;

    /**
     * Достает значение выполненности по habitId и дате.
     * @param habitId
     * @param date
     * @return
     */
    Boolean getLocalDateMark(Long habitId, LocalDate date) throws SQLException;

    /**
     * Отчищает хранилище, используется для удобного тестирования.
     */
    void clear() throws SQLException;

}
