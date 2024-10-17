package org.example.in.console;

import org.example.model.User;
import org.example.service.impl.HabitHistoryServiceImpl;
import org.example.service.impl.HabitServiceImpl;
import org.example.service.impl.UserServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Консольный интерфейс
 */
public class ConsoleInterface {

    /**
     * Сервис привычек.
     */
    private final HabitServiceImpl habitService;

    /**
     * Сервис истории привычек.
     */
    private final HabitHistoryServiceImpl habitHistoryService;

    /**
     * Сервис пользоватеелей.
     */
    private final UserServiceImpl userService;

    /**
     * Конструктор консольного интерфеейса.
     * @param habitService
     * @param habitHistoryService
     * @param userService
     */
    public ConsoleInterface(
            HabitServiceImpl habitService,
            HabitHistoryServiceImpl habitHistoryService,
            UserServiceImpl userService) {
        this.habitService = habitService;
        this.habitHistoryService = habitHistoryService;
        this.userService = userService;
    }

    /**
     * Запускает отображение консольного интерфейса.
     * @throws IOException
     */
    public void run() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        ConsoleService consoleService = new ConsoleService();
        consoleService.populate(habitService, habitHistoryService, userService);

        System.out.println("Добро пожаловать");

        while (true) {
            System.out.println("Войти или зарегистрироваться (Вход - 1, Регистраиця - 2)");
            String temp = reader.readLine();
            User authenticatedUser;

            if (temp.equals("2")) {
                if (consoleService.registration(reader, userService) < 0) {
                    continue;
                }
                System.out.println("Регистрация прошла успешно!");
            } else if (temp.equals("1")) {
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
                        case "1" ->
                                consoleService.createHabit(reader, habitService, authenticatedUser.getId());
                        case "2" ->
                                consoleService.showHabitsByUserId(reader, habitService, authenticatedUser.getId());
                        case "3" ->
                                consoleService.changeHabit(reader, authenticatedUser, habitService);
                        case "4" ->
                                consoleService.markHabit(authenticatedUser, reader, habitService, habitHistoryService);
                        case "5" ->
                                consoleService.showHistoryOfHabit(reader, habitHistoryService, habitService, authenticatedUser.getId());
                        case "6" ->
                                consoleService.showStatistic(reader, habitHistoryService, habitService, authenticatedUser);
                        case "7" ->
                                consoleService.deleteHabit(reader, habitService, authenticatedUser);
                        case "8" ->
                                consoleService.showProfile(authenticatedUser);
                        case "9" ->
                                consoleService.changeProfile(reader, authenticatedUser, userService);
                        case "10" ->
                                consoleService.changePassword(reader, authenticatedUser, userService);
                        case "11" ->
                        {
                            if (consoleService.deleteProfile(reader, userService, authenticatedUser)) {
                                auth = false;
                            }
                        }

                        case "12" -> auth = false;
                    }
                }
            }
        }
    }
}
