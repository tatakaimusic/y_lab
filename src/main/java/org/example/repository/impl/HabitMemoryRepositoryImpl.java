package org.example.repository.impl;

import org.example.model.Habit;
import org.example.repository.HabitMemoryRepository;

import java.time.LocalDate;
import java.util.*;

public class HabitMemoryRepositoryImpl implements HabitMemoryRepository {

    private static Long counter = 100L;

    private final Map<Long, Map<String, Habit>> habits = new LinkedHashMap<>();

    @Override
    public Habit create(Long userId, Habit habit) {
        habit.setId(counter++);
        habit.setCreateDate(LocalDate.now());
        if (!habits.containsKey(userId)) {
            habits.put(userId, new HashMap<>());
        }
        habits.get(userId).put(habit.getTitle(), habit);
        return habit;
    }

    @Override
    public Habit create(Long userId, Habit habit, LocalDate createDate) {
        habit.setId(counter++);
        habit.setCreateDate(createDate);
        if (!habits.containsKey(userId)) {
            habits.put(userId, new HashMap<>());
        }
        habits.get(userId).put(habit.getTitle(), habit);
        return habit;
    }

    @Override
    public Optional<Habit> get(Long userId, String habitTitle) {
        if (habits.containsKey(userId)) {
            return Optional.ofNullable(habits.get(userId).get(habitTitle));
        }
        return Optional.empty();
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
    public List<Habit> getAllHabits() {
        List<Habit> result = new ArrayList<>();
        for (Map.Entry<Long, Map<String, Habit>> entry : habits.entrySet()) {
            result.addAll(entry.getValue().values());
        }
        return result;
    }

    @Override
    public void update(Long userId, String oldHabitTitle, Habit habit) {
        if (habits.containsKey(userId)) {
            habits.get(userId).remove(oldHabitTitle);
            habits.get(userId).put(habit.getTitle(), habit);
        }
    }

    @Override
    public void delete(Long userId, String habitTitle) {
        if (habits.containsKey(userId)) {
            habits.get(userId).remove(habitTitle);
            if (habits.get(userId).isEmpty()) {
                habits.remove(userId);
            }
        }
    }

    @Override
    public void clear() {
        habits.clear();
    }

}
