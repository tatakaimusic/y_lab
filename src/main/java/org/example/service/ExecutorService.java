package org.example.service;

import org.example.model.Habit;
import org.example.service.impl.HabitHistoryServiceImpl;
import org.example.service.impl.HabitServiceImpl;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ExecutorService {

    private static final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(5);

    /**
     * Запускает ScheduledExecutorService.
     * Каждую полночь переебирает все привычки в хранилище и добавляет новую дату в хранилище историй каждой привычки.
     * delay - количество милисекунд до полуночи при старте приложения.
     *
     * @param habitService
     * @param habitHistoryService
     */
    public static void execute(
            HabitServiceImpl habitService,
            HabitHistoryServiceImpl habitHistoryService
    ) {
        LocalTime currentTime = LocalTime.now();
        LocalTime midnight = LocalTime.MIDNIGHT;
        long dayInMillis = 86400000;
        long delay = dayInMillis - midnight.until(currentTime, ChronoUnit.MILLIS);

        executorService.scheduleAtFixedRate(() -> {
            for (Habit habit : habitService.getAllHabits()) {
                habitHistoryService.create(habit.getId());
            }
        }, delay, dayInMillis, TimeUnit.MILLISECONDS);
    }



}
