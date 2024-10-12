package org.example.service;

import org.example.model.Habit;
import org.example.model.HabitPeriod;
import org.example.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface HabitService {

    Habit create(Long userId, Habit habit);

    Habit create(Long userId, Habit habit, LocalDate date);

    Habit get(Long userId, String habitTitle);

    List<Habit> getAllHabitsByUserId(Long userId);

    List<Habit> getAllHabitsByUserIdOrderedByDate(Long userId, Order order);

    List<Habit> getAllHabitsByUserIdAndPeriod(Long userId, HabitPeriod period);

    List<Habit> getAllHabits();

    void update(Long userId, String habitTitle, Habit habit);

    void delete(Long userId, String habitTitle, Long habitId);

}
