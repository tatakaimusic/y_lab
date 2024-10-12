package org.example;


import org.example.model.Habit;
import org.example.model.HabitPeriod;
import org.example.model.User;
import org.example.repository.impl.HabitHistoryMemoryRepositoryImpl;
import org.example.repository.impl.HabitMemoryRepositoryImpl;
import org.example.repository.impl.UserMemoryRepositoryImpl;
import org.example.service.impl.HabitHistoryServiceImpl;
import org.example.service.impl.HabitServiceImpl;
import org.example.service.impl.UserServiceImpl;

import java.time.LocalDate;
import java.time.Month;

public class Main {
    public static void main(String[] args) {
        HabitMemoryRepositoryImpl habitMemoryRepository = new HabitMemoryRepositoryImpl();
        HabitHistoryMemoryRepositoryImpl habitHistoryMemoryRepository = new HabitHistoryMemoryRepositoryImpl();
        HabitServiceImpl habitService = new HabitServiceImpl(habitHistoryMemoryRepository, habitMemoryRepository);
        HabitHistoryServiceImpl habitHistoryService = new HabitHistoryServiceImpl(habitHistoryMemoryRepository, habitMemoryRepository);
        UserMemoryRepositoryImpl userMemoryRepository = new UserMemoryRepositoryImpl();
        UserServiceImpl userService = new UserServiceImpl(userMemoryRepository);

        User user = new User("Nikita", "email", "password");
        user = userService.create(user);
        System.out.println(user);

        Habit habit = new Habit("title", "description", HabitPeriod.DAILY);
        habit = habitService.create(user.getId(), habit, LocalDate.of(2024, Month.OCTOBER, 9));
        System.out.println(habit);

        habitHistoryService.create(habit.getId(), LocalDate.of(2024, Month.OCTOBER, 10));
        habitHistoryService.create(habit.getId(), LocalDate.of(2024, Month.OCTOBER, 11));
        habitHistoryService.create(habit.getId(), LocalDate.of(2024, Month.OCTOBER, 12));

        habitHistoryService.mark(habit.getId(), user.getId(), LocalDate.of(2024, Month.OCTOBER, 9));
        habitHistoryService.mark(habit.getId(), user.getId(), LocalDate.of(2024, Month.OCTOBER, 11));
        habitHistoryService.mark(habit.getId(), user.getId(), LocalDate.of(2024, Month.OCTOBER, 12));

        System.out.println(habitHistoryService.getHabitHistory(habit.getId()));

        System.out.println(habitHistoryService.getCurrentStreak(habit.getId()));
        System.out.println(habitHistoryService.getMaxStreak(habit.getId()));
    }
}