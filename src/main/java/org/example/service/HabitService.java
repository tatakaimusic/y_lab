package org.example.service;

import org.example.model.Habit;

import java.util.List;

public interface HabitService {

    Habit create(Long userId, Habit habit);

    Habit get(Long userId, Long habitId);

    List<Habit> getAllHabitsByUserId(Long userId);

    List<Habit> getAllHabits();

    void update(Long userId, Long habitId, Habit habit);

    void delete(Long userId, Long habitId);

}
