package org.example.service;

import org.example.model.Period;

import java.time.LocalDate;
import java.util.Map;

/**
 * Интерфейс сервиса истории привычек.
 */
public interface HabitHistoryService {

    /**
     * Меня значение выполненности привычки за сегоднешнюю дату по habitId на противоположное.
     * @param habitId
     */
    void mark(Long habitId, Long userId);

    /**
     * Меня значение выполненности привычки за указнную дату по habitId на противоположное.
     * @param habitId
     * @param date
     */
    void mark(Long habitId, Long userId, LocalDate date);

    /**
     * Доавбляет историю в сегоднешний день по habitId.
     * @param habitId
     */
    void create(Long habitId);

    /**
     * Доавбляет историю в указанный день по habitId.
     * @param habitId
     * @param date
     */
    void create(Long habitId, LocalDate date);

    /**
     * Удаляет историю привычки по habitId.
     * @param habitId
     */
    void delete(Long habitId);

    /**
     * Достает историю привычки по habitId.
     * Вернет null, если у привычки нет истории.
     * @param habitId
     * @return
     */
    Map<LocalDate, Boolean> getHabitHistory(Long habitId);

    /**
     * Достает значение выполненности по habitId и дате.
     * @param habitId
     * @param date
     * @return
     */
    Boolean getLocalDateMark(Long habitId, LocalDate date);

    /**
     * Возвращает текущий стрик для привычки по habitId.
     * @param habitId
     * @return
     */
    int getCurrentStreak(Long habitId);

    /**
     * Возвращает максимальный стрик для привычки по habitId.
     * @param habitId
     * @return
     */
    int getMaxStreak(Long habitId);

    /**
     * Возвращает процент выполненности ппривычки за указанный период.
     * @param habitId
     * @param period
     * @return
     */
    Float getPercentOfHabitForPeriod(Long habitId, Period period);

}
