package org.example.repository.impl;


import org.example.repository.HabitHistoryMemoryRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HabitHistoryMemoryRepositoryImpl implements HabitHistoryMemoryRepository {

    Map<Long, List<LocalDate>> habitHistory = new HashMap<>();

    public void create(Long habitId, LocalDate date) {
        if (!habitHistory.containsKey(habitId)) {
            habitHistory.put(habitId, new ArrayList<>());
        }
        habitHistory.get(habitId).add(date);
    }

    public List<LocalDate> getHabitHistory(Long habitId) {
        if (habitHistory.containsKey(habitId)) {
            return habitHistory.get(habitId);
        } else {
            return new ArrayList<>();
        }
    }

}
