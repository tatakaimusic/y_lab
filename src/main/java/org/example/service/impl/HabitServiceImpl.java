package org.example.service.impl;

import org.example.model.Habit;
import org.example.model.HabitPeriod;
import org.example.model.Order;
import org.example.repository.impl.HabitHistoryMemoryRepositoryImpl;
import org.example.repository.impl.HabitMemoryRepositoryImpl;
import org.example.service.HabitService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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
        return create(userId, habit, LocalDate.now());
    }

    @Override
    public Habit create(Long userId, Habit habit, LocalDate date) {
        if (habitMemoryRepository.get(userId, habit.getTitle()).isPresent()) {
            throw new IllegalArgumentException("Habit with this title already exists");
        }
        habit = habitMemoryRepository.create(userId, habit, date);
        habitHistoryMemoryRepository.create(habit.getId(), date);
        return habit;
    }

    @Override
    public Habit get(Long userId, String habitTitle) {
        return habitMemoryRepository.get(userId, habitTitle)
                .orElseThrow(
                        () -> new IllegalArgumentException("Habit with this title doesn't exist!")
                );
    }

    @Override
    public List<Habit> getAllHabitsByUserId(Long userId) {
        return habitMemoryRepository.getAllHabitsByUserId(userId);
    }

    @Override
    public List<Habit> getAllHabitsByUserIdOrderedByDate(Long userId, Order order) {
        List<Habit> habits = habitMemoryRepository.getAllHabitsByUserId(userId);
        List<Habit> sortedHabits = new ArrayList<>(habits);
        switch (order) {
            case ASC:
                sortedHabits.sort(Comparator.comparing(Habit::getCreateDate));
                break;
            case DESC:
                sortedHabits.sort(Comparator.comparing(Habit::getCreateDate).reversed());
                break;
        }
        return sortedHabits;
    }

    @Override
    public List<Habit> getAllHabitsByUserIdAndPeriod(Long userId, HabitPeriod period) {
        List<Habit> habits = habitMemoryRepository.getAllHabitsByUserId(userId);
        List<Habit> result = new ArrayList<>();
        for (Habit habit : habits) {
            if (habit.getPeriod().equals(period)) {
                result.add(habit);
            }
        }
        return result;
    }

    @Override
    public List<Habit> getAllHabits() {
        return habitMemoryRepository.getAllHabits();
    }

    @Override
    public void update(Long userId, String habitTitle, Habit habit) {
        if (!habitTitle.equals(habit.getTitle())) {
            Habit hab = habitMemoryRepository.get(userId, habit.getTitle()).orElse(null);
            if (hab != null) {
                throw new IllegalArgumentException("Habit with this title already exist!");
            }
        }
        habitMemoryRepository.update(userId, habitTitle, habit);
    }

    @Override
    public void delete(Long userId, String habitTitle, Long habitId) {
        habitMemoryRepository.delete(userId, habitTitle);
        habitHistoryMemoryRepository.delete(habitId);
    }

}
