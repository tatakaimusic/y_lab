package org.example.in.console;


import org.example.repository.impl.HabitHistoryMemoryRepositoryImpl;
import org.example.repository.impl.HabitMemoryRepositoryImpl;
import org.example.repository.impl.UserMemoryRepositoryImpl;
import org.example.service.ExecutorService;
import org.example.service.impl.HabitHistoryServiceImpl;
import org.example.service.impl.HabitServiceImpl;
import org.example.service.impl.UserServiceImpl;

import java.io.IOException;


public class App {
    public static void main(String[] args) throws IOException {
        HabitMemoryRepositoryImpl habitMemoryRepository = new HabitMemoryRepositoryImpl();
        HabitHistoryMemoryRepositoryImpl habitHistoryMemoryRepository = new HabitHistoryMemoryRepositoryImpl();
        HabitServiceImpl habitService = new HabitServiceImpl(habitHistoryMemoryRepository, habitMemoryRepository);
        HabitHistoryServiceImpl habitHistoryService = new HabitHistoryServiceImpl(
                habitHistoryMemoryRepository, habitMemoryRepository
        );
        UserMemoryRepositoryImpl userMemoryRepository = new UserMemoryRepositoryImpl();
        UserServiceImpl userService = new UserServiceImpl(userMemoryRepository);

        ExecutorService.execute(habitService, habitHistoryService);

        ConsoleInterface consoleInterface = new ConsoleInterface(habitService, habitHistoryService, userService);
        consoleInterface.run();
    }
}