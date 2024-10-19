package org.example.service.impl;

import org.example.model.Habit;
import org.example.model.HabitPeriod;
import org.example.model.Order;
import org.example.repository.HabitHistoryRepository;
import org.example.repository.HabitRepository;
import org.example.service.HabitService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class HabitServiceImpl implements HabitService {

    private final HabitRepository habitRepository;
    private final HabitHistoryRepository habitHistoryRepository;

    public HabitServiceImpl(HabitRepository habitRepository, HabitHistoryRepository habitHistoryRepository) {
        this.habitRepository = habitRepository;
        this.habitHistoryRepository = habitHistoryRepository;
    }

    @Override
    public Habit create(Long userId, Habit habit) throws SQLException {
        return create(userId, habit, LocalDate.now());
    }

    @Override
    public Habit create(Long userId, Habit habit, LocalDate date) throws SQLException {
        if (habitRepository.get(userId, habit.getTitle()).isPresent()) {
            throw new IllegalArgumentException("Habit with this title already exists");
        }
        habit = habitRepository.create(userId, habit, date);
        habitHistoryRepository.create(habit.getId(), date);
        return habit;
    }

    @Override
    public Habit get(Long userId, String habitTitle) throws SQLException {
        return habitRepository.get(userId, habitTitle)
                .orElseThrow(
                        () -> new IllegalArgumentException("Habit with this title doesn't exist!")
                );
    }

    @Override
    public List<Habit> getAllHabitsByUserId(Long userId) throws SQLException {
        return habitRepository.getAllHabitsByUserId(userId);
    }

    @Override
    public List<Habit> getAllHabitsByUserIdOrderedByDate(Long userId, Order order) throws SQLException {
        List<Habit> habits = habitRepository.getAllHabitsByUserId(userId);
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
    public List<Habit> getAllHabitsByUserIdAndPeriod(Long userId, HabitPeriod period) throws SQLException {
        List<Habit> habits = habitRepository.getAllHabitsByUserId(userId);
        List<Habit> result = new ArrayList<>();
        for (Habit habit : habits) {
            if (habit.getPeriod().equals(period)) {
                result.add(habit);
            }
        }
        return result;
    }

    @Override
    public List<Habit> getAllHabits() throws SQLException {
        return habitRepository.getAllHabits();
    }

    @Override
    public void update(Long userId, String habitTitle, Habit habit) throws SQLException {
        if (!habitTitle.equals(habit.getTitle())) {
            Habit hab = habitRepository.get(userId, habit.getTitle()).orElse(null);
            if (hab != null) {
                throw new IllegalArgumentException("Habit with this title already exist!");
            }
        }
        habitRepository.update(userId, habit);
    }

    @Override
    public void delete(Long userId, String habitTitle, Long habitId) throws SQLException {
        habitRepository.delete(userId, habitTitle);
        habitHistoryRepository.delete(habitId);
    }

}
