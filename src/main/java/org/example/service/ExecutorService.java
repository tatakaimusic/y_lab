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
//                setStreak(habit, habitHistoryService);
            }
        }, delay, dayInMillis, TimeUnit.MILLISECONDS);
    }

//    private static void setStreak(Habit habit, HabitHistoryServiceImpl habitHistoryService) {
//        boolean yesterdayMark = habitHistoryService.getLocalDateMark(
//                habit.getId(), LocalDate.now().minusDays(1)
//        );
//        if (!yesterdayMark) {
//            habit.setCurrentStreak(0);
//        } else {
//            habit.setCurrentStreak(habit.getCurrentStreak() + 1);
//            habit.setMaxStreak(Math.max(habit.getCurrentStreak(), habit.getMaxStreak()));
//        }
//    }

}
