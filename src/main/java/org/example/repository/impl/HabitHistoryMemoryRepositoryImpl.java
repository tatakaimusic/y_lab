package org.example.repository.impl;


import org.example.model.Period;
import org.example.repository.HabitHistoryMemoryRepository;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;


public class HabitHistoryMemoryRepositoryImpl implements HabitHistoryMemoryRepository {

    Map<Long, Map<LocalDate, Boolean>> habitHistory = new LinkedHashMap<>();

    @Override
    public void mark(Long habitId, LocalDate date) {
        Map<LocalDate, Boolean> dates = habitHistory.get(habitId);
        dates.put(date, !getLocalDateMark(habitId, LocalDate.now()));
    }

    @Override
    public void create(Long habitId, LocalDate date) {
        if (!habitHistory.containsKey(habitId)) {
            habitHistory.put(habitId, new LinkedHashMap<>());
        }
        habitHistory.get(habitId).put(date, false);

    }

    @Override
    public void delete(Long habitId) {
        habitHistory.remove(habitId);
    }

    @Override
    public Map<LocalDate, Boolean> getHabitHistory(Long habitId) {
        return habitHistory.get(habitId);
    }

    @Override
    public Boolean getLocalDateMark(Long habitId, LocalDate date) {
        return habitHistory.get(habitId).get(date);
    }

}
