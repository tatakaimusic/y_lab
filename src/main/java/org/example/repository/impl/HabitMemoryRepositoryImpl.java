package org.example.repository.impl;

import org.example.model.Habit;
import org.example.repository.HabitMemoryRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HabitMemoryRepositoryImpl implements HabitMemoryRepository {

    private static Long counter = 100L;

    private final Map<Long, Map<Long, Habit>> habits = new HashMap<>();

    @Override
    public Habit create(Long userId, Habit habit) {
        habit.setId(counter++);
        if (!habits.containsKey(userId)) {
            habits.put(userId, new HashMap<>());
        }
        habits.get(userId).put(habit.getId(), habit);
        return habit;
    }

    @Override
    public List<Habit> getAllHabitsByUserId(Long userId) {
        if (habits.containsKey(userId)) {
            return habits.get(userId).values().stream().toList();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public void update(Long userId, Long habitId, Habit habit) {
        if (habits.containsKey(userId)) {
            habits.get(userId).put(habitId, habit);
        }
    }

    @Override
    public void delete(Long userId, Long habitId) {
        if (habits.containsKey(userId)) {
            habits.get(userId).remove(habitId);
            if (habits.get(userId).isEmpty()) {
                habits.remove(userId);
            }
        }
    }

}
