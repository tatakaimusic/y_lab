package org.example.service;

import org.example.model.HabitHistoryMark;
import org.example.model.Period;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Интерфейс сервиса истории привычек.
 */
public interface HabitHistoryService {

    /**
     * Меня значение выполненности привычки за сегоднешнюю дату по habitId на противоположное.
     * @param habitId
     */
    void mark(Long habitId, Long userId) throws SQLException;

    /**
     * Меня значение выполненности привычки за указнную дату по habitId на противоположное.
     * @param habitId
     * @param date
     */
    void mark(Long habitId, Long userId, LocalDate date) throws SQLException;

    /**
     * Доавбляет историю в сегоднешний день по habitId.
     * @param habitId
     */
    void create(Long habitId) throws SQLException;

    /**
     * Доавбляет историю в указанный день по habitId.
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
    List<HabitHistoryMark> getHabitHistory(Long habitId) throws SQLException;

    /**
     * Достает значение выполненности по habitId и дате.
     * @param habitId
     * @param date
     * @return
     */
    Boolean getLocalDateMark(Long habitId, LocalDate date) throws SQLException;

    /**
     * Возвращает текущий стрик для привычки по habitId.
     * @param habitId
     * @return
     */
    int getCurrentStreak(Long habitId) throws SQLException;

    /**
     * Возвращает максимальный стрик для привычки по habitId.
     * @param habitId
     * @return
     */
    int getMaxStreak(Long habitId) throws SQLException;

    /**
     * Возвращает процент выполненности ппривычки за указанный период.
     * @param habitId
     * @param period
     * @return
     */
    Float getPercentOfHabitForPeriod(Long habitId, Period period) throws SQLException;

}
