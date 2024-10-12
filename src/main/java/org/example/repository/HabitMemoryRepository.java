package org.example.repository;

import org.example.model.Habit;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HabitMemoryRepository {

    Habit create(Long userId, Habit habit);

    Habit create(Long userId, Habit habit, LocalDate createDate);

    Optional<Habit> get(Long userId, String habitTitle);

    List<Habit> getAllHabitsByUserId(Long userId);

    List<Habit> getAllHabits();

    void update(Long userId, String habitTitle, Habit habit);

    void delete(Long userId, String habitTitle);

}
