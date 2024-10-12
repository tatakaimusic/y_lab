package org.example.repository;

import org.example.model.Habit;

import java.time.LocalDate;
import java.util.List;

public interface HabitMemoryRepository {

    Habit create(Long userId, Habit habit);

    Habit create(Long userId, Habit habit, LocalDate createDate);

    Habit get(Long userId, Long habitId);

    List<Habit> getAllHabitsByUserId(Long userId);

    List<Habit> getAllHabits();

    void update(Long userId, Long habitId, Habit habit);

    void delete(Long userId, Long habitId);

}
