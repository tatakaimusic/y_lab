package org.example;


import org.example.model.Habit;
import org.example.model.Period;
import org.example.model.User;
import org.example.repository.impl.HabitHistoryMemoryRepositoryImpl;
import org.example.repository.impl.HabitMemoryRepositoryImpl;
import org.example.repository.impl.UserMemoryRepositoryImpl;
import org.example.service.ExecutorService;
import org.example.service.impl.HabitHistoryServiceImpl;
import org.example.service.impl.HabitServiceImpl;



public class Main {
    public static void main(String[] args) {

        HabitMemoryRepositoryImpl habitMemoryRepository = new HabitMemoryRepositoryImpl();
        HabitHistoryMemoryRepositoryImpl habitHistoryMemoryRepository = new HabitHistoryMemoryRepositoryImpl();
        UserMemoryRepositoryImpl userMemoryRepository = new UserMemoryRepositoryImpl();
        HabitHistoryServiceImpl habitHistoryService = new HabitHistoryServiceImpl(habitHistoryMemoryRepository, habitMemoryRepository);
        HabitServiceImpl habitService = new HabitServiceImpl(habitHistoryMemoryRepository, habitMemoryRepository);


        User user = new User("Nikita", "mail@mail.ru", "password");
        user = userMemoryRepository.create(user);
        System.out.println(user);
        Habit habit = new Habit("habit", "description", Period.DAILY);
        habit = habitService.create(user.getId(), habit);
        System.out.println(habit);
        ExecutorService.execute(habitService, habitHistoryService);

//        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(5);
//
//        executorService.scheduleAtFixedRate(() -> {
//            for (Habit habitt : habitService.getAllHabits()) {
//                habitHistoryService.create(habitt.getId());
//
//            }
//            System.out.println("Hello from executor service");
//            System.out.println(habitHistoryService.getHabitHistory(100L));
//        }, 2000, 10000, TimeUnit.MILLISECONDS);


    }
}