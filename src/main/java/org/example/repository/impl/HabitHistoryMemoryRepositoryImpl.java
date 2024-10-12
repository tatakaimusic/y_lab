package org.example.repository.impl;


import org.example.repository.HabitHistoryMemoryRepository;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Класс для хранения и обработки истории выолнения привычек.
 */
public class HabitHistoryMemoryRepositoryImpl implements HabitHistoryMemoryRepository {

    /**
     * Хранилище истории привычки, где ключ внешней мапы - habitId,
     * ключ внутренней мапы - дата выполнения, и значение внутренней мапы - boolean,
     * где true - выполнено, false - невыполнено.
     */
    Map<Long, Map<LocalDate, Boolean>> habitHistory = new LinkedHashMap<>();

    /**
     * Меня значение выполненности привычки за указнную дату по habitId на противоположное.
     * @param habitId
     * @param date
     */
    @Override
    public void mark(Long habitId, LocalDate date) {
        Map<LocalDate, Boolean> dates = habitHistory.get(habitId);
        dates.put(date, !getLocalDateMark(habitId, LocalDate.now()));
    }

    /**
     * Доавбляет историю указанный день по habitId.
     * @param habitId
     * @param date
     */
    @Override
    public void create(Long habitId, LocalDate date) {
        if (!habitHistory.containsKey(habitId)) {
            habitHistory.put(habitId, new LinkedHashMap<>());
        }
        habitHistory.get(habitId).put(date, false);

    }

    /**
     * Удаляет историю привычки по habitId.
     * @param habitId
     */
    @Override
    public void delete(Long habitId) {
        habitHistory.remove(habitId);
    }

    /**
     * Достает историю привычки по habitId.
     * Вернет null, если у привычки нет истории.
     * @param habitId
     * @return
     */
    @Override
    public Map<LocalDate, Boolean> getHabitHistory(Long habitId) {
        return habitHistory.get(habitId);
    }

    /**
     * Достает значение выполненности по habitId и дате.
     * @param habitId
     * @param date
     * @return
     */
    @Override
    public Boolean getLocalDateMark(Long habitId, LocalDate date) {
        return habitHistory.get(habitId).get(date);
    }

    /**
     * Отчищает хранилище, используется для удобного тестирования.
     */
    @Override
    public void clear(){
        habitHistory.clear();
    }

}
