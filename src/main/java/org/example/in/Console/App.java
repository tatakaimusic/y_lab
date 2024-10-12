package org.example.in.Console;


import org.example.model.User;
import org.example.repository.impl.HabitHistoryMemoryRepositoryImpl;
import org.example.repository.impl.HabitMemoryRepositoryImpl;
import org.example.repository.impl.UserMemoryRepositoryImpl;
import org.example.service.ExecutorService;
import org.example.service.impl.HabitHistoryServiceImpl;
import org.example.service.impl.HabitServiceImpl;
import org.example.service.impl.UserServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class App {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        HabitMemoryRepositoryImpl habitMemoryRepository = new HabitMemoryRepositoryImpl();
        HabitHistoryMemoryRepositoryImpl habitHistoryMemoryRepository = new HabitHistoryMemoryRepositoryImpl();
        HabitServiceImpl habitService = new HabitServiceImpl(habitHistoryMemoryRepository, habitMemoryRepository);
        HabitHistoryServiceImpl habitHistoryService = new HabitHistoryServiceImpl(habitHistoryMemoryRepository, habitMemoryRepository);
        UserMemoryRepositoryImpl userMemoryRepository = new UserMemoryRepositoryImpl();
        UserServiceImpl userService = new UserServiceImpl(userMemoryRepository);
        ConsoleService consoleService = new ConsoleService();

        ExecutorService.execute(habitService, habitHistoryService);

        consoleService.populate(habitService, habitHistoryService, userService);

        System.out.println("Добро пожаловать");

        while (true) {
            System.out.println("Войти или зарегистрироваться (введите 'Вход' или 'Регистрация')");
            String temp = reader.readLine();
            User authenticatedUser;

            if (temp.equals("Регистрация")) {
                if (consoleService.registration(reader, userService) < 0) {
                    continue;
                }
                System.out.println("Регистрация прошла успешно!");
            } else if (temp.equals("Вход")) {
                authenticatedUser = consoleService.authentication(reader, userService);
                if (authenticatedUser == null) {
                    continue;
                }
                System.out.println("Вы успешно аутентифицировались!");
                System.out.println();
                boolean auth = true;
                while (auth) {
                    consoleService.showNavigation();
                    String operation = reader.readLine();
                    switch (operation) {
                        case "Создать привычку":
                            consoleService.createHabit(reader, habitService, authenticatedUser.getId());
                            break;
                        case "Привычки":
                            consoleService.showHabitsByUserId(reader, habitService, authenticatedUser.getId());
                            break;
                        case "Изменить привычку":
                            consoleService.changeHabit(reader, authenticatedUser, habitService);
                            break;
                        case "Отметить":
                            consoleService.markHabit(authenticatedUser, reader, habitService, habitHistoryService);
                            break;
                        case "История":
                            consoleService.showHistoryOfHabit(reader, habitHistoryService, habitService, authenticatedUser.getId());
                            break;
                        case "Статистика":
                            consoleService.showStatistic(reader, habitHistoryService, habitService, authenticatedUser);
                            break;
                        case "Удалить привычку":
                            consoleService.deleteHabit(reader, habitService, authenticatedUser);
                            break;
                        case "Профиль":
                            consoleService.showProfile(authenticatedUser);
                            break;
                        case "Изменить профиль":
                            consoleService.changeProfile(reader, authenticatedUser, userService);
                            break;
                        case "Удалить аккаунт":
                            if (consoleService.deleteProfile(reader, userService, authenticatedUser)) {
                                auth = false;
                            }
                            break;
                        case "Поменять пароль":
                            consoleService.changePassword(reader, authenticatedUser, userService);
                            break;
                        case "Выход":
                            auth = false;
                    }
                }
            }
        }
    }
}