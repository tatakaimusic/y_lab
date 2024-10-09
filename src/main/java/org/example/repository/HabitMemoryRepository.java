package org.example.repository;

import org.example.model.Habit;

import java.util.List;

public interface HabitMemoryRepository {

    Habit create(Long userId, Habit habit);

    List<Habit> getAllHabitsByUserId(Long userId);

    void update(Long userId, Long habitId, Habit habit);

    void delete(Long userId, Long habitId);

}
