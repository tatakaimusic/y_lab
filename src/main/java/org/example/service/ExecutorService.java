package org.example.service;

import org.example.model.Habit;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ExecutorService {

    /**
     * Создание экземпляра ScheduledExecutorService с количством потоков, равным 5.
     */
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
            HabitService habitService,
            HabitHistoryService habitHistoryService
    ) {
        LocalTime currentTime = LocalTime.now();
        LocalTime midnight = LocalTime.MIDNIGHT;
        long dayInMillis = 86400000;
        long delay = dayInMillis - midnight.until(currentTime, ChronoUnit.MILLIS);

        executorService.scheduleAtFixedRate(() -> {
            try {
                for (Habit habit : habitService.getAllHabits()) {
                    habitHistoryService.create(habit.getId());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, delay, dayInMillis, TimeUnit.MILLISECONDS);
    }

}
