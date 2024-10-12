package org.example.repository.impl;


import org.example.repository.HabitHistoryMemoryRepository;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;


public class HabitHistoryMemoryRepositoryImpl implements HabitHistoryMemoryRepository {

    Map<Long, Map<LocalDate, Boolean>> habitHistory = new LinkedHashMap<>();

    @Override
    public void mark(Long habitId) {
        Map<LocalDate, Boolean> dates = habitHistory.get(habitId);
        dates.put(LocalDate.now(), !getLocalDateMark(habitId, LocalDate.now()));
    }

    @Override
    public void create(Long habitId) {
        if (!habitHistory.containsKey(habitId)) {
            habitHistory.put(habitId, new LinkedHashMap<>());
        }
        habitHistory.get(habitId).put(LocalDate.now(), false);

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
