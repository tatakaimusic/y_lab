package org.example.service.impl;

import org.example.model.Habit;
import org.example.repository.impl.HabitHistoryMemoryRepositoryImpl;
import org.example.repository.impl.HabitMemoryRepositoryImpl;
import org.example.service.HabitService;

import java.time.LocalDate;
import java.util.List;


public class HabitServiceImpl implements HabitService {

    private final HabitHistoryMemoryRepositoryImpl habitHistoryMemoryRepository;
    private final HabitMemoryRepositoryImpl habitMemoryRepository;

    public HabitServiceImpl(
            HabitHistoryMemoryRepositoryImpl habitHistoryMemoryRepository,
            HabitMemoryRepositoryImpl habitMemoryRepository
    ) {
        this.habitHistoryMemoryRepository = habitHistoryMemoryRepository;
        this.habitMemoryRepository = habitMemoryRepository;
    }

    @Override
    public Habit create(Long userId, Habit habit) {
        habit = habitMemoryRepository.create(userId, habit);
        habitHistoryMemoryRepository.create(habit.getId(), LocalDate.now());
        return habit;
    }

    @Override
    public Habit create(Long userId, Habit habit, LocalDate date) {
        habit = habitMemoryRepository.create(userId, habit);
        habitHistoryMemoryRepository.create(habit.getId(), date);
        return habit;
    }

    @Override
    public Habit get(Long userId, Long habitId) {
        return habitMemoryRepository.get(userId, habitId);
    }

    @Override
    public List<Habit> getAllHabitsByUserId(Long userId) {
        return habitMemoryRepository.getAllHabitsByUserId(userId);
    }

    @Override
    public List<Habit> getAllHabits() {
        return habitMemoryRepository.getAllHabits();
    }

    @Override
    public void update(Long userId, Long habitId, Habit habit) {
        habitMemoryRepository.update(userId, habitId, habit);
    }

    @Override
    public void delete(Long userId, Long habitId) {
        habitMemoryRepository.delete(userId, habitId);
        habitHistoryMemoryRepository.delete(habitId);
    }

}
