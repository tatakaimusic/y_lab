package org.example.in.console;

import org.example.model.*;
import org.example.service.HabitHistoryService;
import org.example.service.HabitService;
import org.example.service.UserService;
import org.example.util.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Сервис используется для создания консольного интерфейса приложения.
 */
public class ConsoleService {

    /**
     * Показывает навигацию для пользователя.
     */
    public void showNavigation() {
        System.out.println(Constant.NAVIGATION);
    }

    /**
     * Регистрация пользователя. Пользователь должен ввести имя, почту и пароль.
     * Вернет -1 и выведет на экран предупреждение, если пользователей с такой почтой уже существует.
     * @param reader
     * @param userService
     * @return
     * @throws IOException
     */
    public int registration(BufferedReader reader, UserService userService) throws IOException, SQLException {
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Введите имя пользователя: ");
        String username = reader.readLine();
        System.out.println("Введите почту: ");
        String mail = reader.readLine();
        System.out.println("Введите пароль: ");
        String password = reader.readLine();
        User user = new User(username, mail, password);
        try {
            userService.create(user);
            System.out.println("------------------------------------------------------------------------");
        } catch (IllegalArgumentException e) {
            System.out.println("Юзер с такой почтой цже существует!");
            System.out.println("------------------------------------------------------------------------");
            return -1;
        }
        return 1;
    }

    /**
     * Ауентефикация пользователя по почте и паролю.
     * Выведет предупреждение, если пароль или почта не совпадают.
     * @param reader
     * @param userService
     * @return
     * @throws IOException
     */
    public User authentication(BufferedReader reader, UserService userService) throws IOException, SQLException {
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Введите почту: ");
        String mail = reader.readLine();
        System.out.println("Введите пароль: ");
        String password = reader.readLine();
        User user;
        try {
            user = userService.get(mail);
        } catch (IllegalArgumentException e) {
            System.out.println("Почта не верна!");
            System.out.println();
            return null;
        }
        if (!user.getPassword().equals(password)) {
            System.out.println("Пароль неверный!");
            System.out.println();
            System.out.println("------------------------------------------------------------------------");
            return null;
        }
        System.out.println("------------------------------------------------------------------------");
        return user;
    }

    /**
     * Выведет список привычек пользователя по его id.
     * @param reader
     * @param habitService
     * @param userId
     * @throws IOException
     */
    public void showHabitsByUserId(BufferedReader reader, HabitService habitService, Long userId) throws IOException, SQLException {
        System.out.println("------------------------------------------------------------------------");
        List<Habit> habits;
        System.out.println(Constant.HABIT_LIST_NAVIGATION);
        String action = reader.readLine();
        switch (action) {
            case "1" -> {
                habits = habitService.getAllHabitsByUserIdOrderedByDate(userId, Order.DESC);
                showHabits(habits);
            }

            case "2" -> {
                habits = habitService.getAllHabitsByUserIdOrderedByDate(userId, Order.ASC);
                showHabits(habits);
            }
            case "3" -> {
                habits = habitService.getAllHabitsByUserIdAndPeriod(userId, HabitPeriod.DAILY);
                showHabits(habits);
            }
            case "4" -> {
                habits = habitService.getAllHabitsByUserIdAndPeriod(userId, HabitPeriod.WEEKLY);
                showHabits(habits);
            }
            case "5" -> System.out.println();
        }
        System.out.println("------------------------------------------------------------------------");
    }

    /**
     * Вывод привычек в консоль.
     * @param habits
     */
    private void showHabits(List<Habit> habits) {
        System.out.println("Ваши привычки: ");
        for (Habit habit : habits) {
            System.out.println(habit);
            System.out.println();
        }
    }

    /**
     * Создание новой привычки по userId.
     * Выведет предупреждение, если привычка с таким названием уже существует.
     * @param reader
     * @param habitService
     * @param userId
     * @throws IOException
     */
    public void createHabit(BufferedReader reader, HabitService habitService, Long userId) throws IOException, SQLException {
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Введите название привычки: ");
        String title = reader.readLine();
        System.out.println("Введите описание привычки: ");
        String description = reader.readLine();
        System.out.println("Выберите периодичность (введите 'DAILY' или 'WEEKLY')");
        String period = reader.readLine();
        HabitPeriod periodEnum = HabitPeriod.valueOf(period);
        Habit habit = new Habit(title, description, periodEnum);
        try {
            habitService.create(userId, habit);
        } catch (IllegalArgumentException e) {
            System.out.println();
            System.out.println("Привычка с таким названием уже существует!");
            System.out.println("------------------------------------------------------------------------");
        }
        System.out.println("------------------------------------------------------------------------");
    }

    /**
     * Выводит историю привычки.
     * @param reader
     * @param habitHistoryService
     * @param habitService
     * @param userId
     * @throws IOException
     */
    public void showHistoryOfHabit(
            BufferedReader reader,
            HabitHistoryService habitHistoryService,
            HabitService habitService,
            Long userId
    ) throws IOException, SQLException {
        System.out.println("------------------------------------------------------------------------");
        showHabits(habitService.getAllHabitsByUserId(userId));
        System.out.println("Введите название привычки: ");
        String title = reader.readLine();
        Habit habit = null;
        try {
            habit = habitService.get(userId, title);
            System.out.println("Текущий streak: " + habitHistoryService.getCurrentStreak(habit.getId()));
            System.out.println("Максимальный streak: " + habitHistoryService.getMaxStreak(habit.getId()));
            for (Map.Entry<LocalDate, Boolean> entry : habitHistoryService.getHabitHistory(habit.getId()).entrySet()) {
                String result = entry.getValue() ? "Выполнена" : "Невыполнена";
                System.out.println(entry.getKey() + ": " + result);
            }
            System.out.println("------------------------------------------------------------------------");
        } catch (IllegalArgumentException e) {
            System.out.println("Привычки с таким названием не существует");
            System.out.println("------------------------------------------------------------------------");
        }

    }

    /**
     * Выводит данные ауентефецированногго пользователя.
     * @param user
     */
    public void showProfile(User user) {
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Ваш профиль: ");
        System.out.println();

        System.out.println("Ваше имя: " + user.getName());
        System.out.println("Ваша почта: " + user.getEmail());
        System.out.println("------------------------------------------------------------------------");
    }

    /**
     * Просит пользователя ввести название првиычки, которую нужно изменить. А также новые данные по привычке.
     * Изменяет привычку.
     * @param reader
     * @param authenticatedUser
     * @param habitService
     * @throws IOException
     */
    public void changeHabit(BufferedReader reader, User authenticatedUser, HabitService habitService) throws IOException, SQLException {
        System.out.println("------------------------------------------------------------------------");

        showHabits(habitService.getAllHabitsByUserId(authenticatedUser.getId()));
        System.out.println("Введите название привычки, которую хотите поменять: ");
        String originalTitle = reader.readLine();
        try {
            Habit originalHabit = habitService.get(authenticatedUser.getId(), originalTitle);
            System.out.println("Введите новое название: ");
            String title = reader.readLine();
            System.out.println("Введите новое описание: ");
            String description = reader.readLine();
            System.out.println("Введите новый период (DAILY, WEEKLY): ");

            HabitPeriod periodEnum = HabitPeriod.valueOf(reader.readLine());
            Habit habit = new Habit(originalHabit.getId(), title, description, periodEnum);
            habit.setCreateDate(originalHabit.getCreateDate());
            try {
                habitService.update(authenticatedUser.getId(), originalTitle, habit);
                System.out.println("Успешно!");
            } catch (IllegalArgumentException e) {
                System.out.println("Привычка с таким названием уже существует!");
            }
            System.out.println("------------------------------------------------------------------------");
        } catch (IllegalArgumentException e) {
            System.out.println("Привычки с таким названием не существует!");
            System.out.println("------------------------------------------------------------------------");
        }

    }

    /**
     * Просит ввести название привычки, которую нужно отметить.
     * Отмечает привычку на противоположное значение. Если првиычка выполнена - ее статус меняется на 'невыполнена'.
     * И наооброт.
     * @param user
     * @param reader
     * @param habitService
     * @param habitHistoryService
     * @throws IOException
     */
    public void markHabit(
            User user,
            BufferedReader reader,
            HabitService habitService,
            HabitHistoryService habitHistoryService
    ) throws IOException, SQLException {
        System.out.println("------------------------------------------------------------------------");
        showHabits(habitService.getAllHabitsByUserId(user.getId()));
        System.out.println("Введите название привычки, которую хотите отметить: ");
        String title = reader.readLine();
        try {
            Habit habit = habitService.get(user.getId(), title);
            habitHistoryService.mark(habit.getId(), user.getId());
            System.out.println("Успешно!");
            System.out.println("------------------------------------------------------------------------");
        } catch (IllegalArgumentException e) {
            System.out.println("Привычки с таким названием не существует!");
            System.out.println("------------------------------------------------------------------------");
        }

    }

    /**
     * Выводит статистику по привычке, название которой было введено.
     * @param reader
     * @param habitHistoryService
     * @param habitService
     * @param user
     * @throws IOException
     */
    public void showStatistic(
            BufferedReader reader,
            HabitHistoryService habitHistoryService,
            HabitService habitService,
            User user
    ) throws IOException, SQLException {
        System.out.println("------------------------------------------------------------------------");
        showHabits(habitService.getAllHabitsByUserId(user.getId()));
        System.out.println("Введите название привычки, у которой вы хотите посмотреть статистику: ");
        String title = reader.readLine();
        try {
            Habit habit = habitService.get(user.getId(), title);
            System.out.println("Введите период за который вы хотите посмотреть статистику (введите: DAY, WEEK или MONTH): ");
            System.out.println("DAY - за прошлый день");
            System.out.println("WEEK - за ближайшие 7 дней");
            System.out.println("MONTH - за этот месяц");
            Period periodEnum = Period.valueOf(reader.readLine());
            System.out.println(
                    "Процент выполнения за данный период: "
                            + habitHistoryService.getPercentOfHabitForPeriod(habit.getId(), periodEnum) + " %"
            );
            System.out.println("------------------------------------------------------------------------");
        } catch (IllegalArgumentException e) {
            System.out.println("Привычки с таким названием не существует");
            System.out.println("------------------------------------------------------------------------");
        }

    }

    /**
     * Просит ввести новые данные пользовтеля и меняет профиль.
     * @param reader
     * @param authenticatedUser
     * @param userService
     * @throws IOException
     */
    public void changeProfile(
            BufferedReader reader,
            User authenticatedUser,
            UserService userService
    ) throws IOException, SQLException {
        System.out.println("------------------------------------------------------------------------");
        showProfile(authenticatedUser);
        System.out.println("Ввеедите новое имя: ");
        String name = reader.readLine();
        System.out.println("Введите новую почту: ");
        String email = reader.readLine();
        if (!Objects.equals(email, authenticatedUser.getEmail())) {
            User user;
            try {
                user = userService.get(email);
            } catch (IllegalArgumentException e) {
                user = null;
            }
            if (user != null) {
                System.out.println("Юзер с такой почтой уже существует!");
                System.out.println("------------------------------------------------------------------------");
            } else {
                setNewProfile(name, email, authenticatedUser, userService);
            }
        } else {
            setNewProfile(name, email, authenticatedUser, userService);
        }
    }

    /**
     * Устанавливает новые значения для ользователя.
     * @param name
     * @param email
     * @param user
     */
    private void setNewProfile(String name, String email, User user, UserService userService) throws SQLException {
        user.setName(name);
        user.setEmail(email);
        userService.update(user);
        System.out.println("Успешно!");
        System.out.println("------------------------------------------------------------------------");
    }

    /**
     * Удаляет профиль
     * @param reader
     * @param userService
     * @param authenticatedUser
     * @return
     * @throws IOException
     */
    public boolean deleteProfile(
            BufferedReader reader,
            UserService userService,
            User authenticatedUser
    ) throws IOException, SQLException {
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Вы уверены? Введите Да или Нет:");
        String action = reader.readLine();
        if (action.equals("Да")) {
            userService.delete(authenticatedUser);
            System.out.println("Успешно!");
            return true;
        }
        System.out.println("------------------------------------------------------------------------");
        return false;
    }

    /**
     * Измееняет пароль пользователя.
     * @param reader
     * @param authenticatedUser
     * @param userService
     * @throws IOException
     */
    public void changePassword(
            BufferedReader reader,
            User authenticatedUser,
            UserService userService
    ) throws IOException, SQLException {
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Введите старый пароль: ");
        String oldPassword = reader.readLine();
        if (!oldPassword.equals(authenticatedUser.getPassword())) {
            System.out.println("Нерпавильный пароль!");
        } else {
            System.out.println("Введите новый пароль: ");
            String newPassword = reader.readLine();
            authenticatedUser.setPassword(newPassword);
            userService.update(authenticatedUser);
            System.out.println("Успешно!");
        }
        System.out.println("------------------------------------------------------------------------");
    }

    /**
     * Удалеение привычки по названию, которое было введено.
     * @param reader
     * @param habitService
     * @param user
     * @throws IOException
     */
    public void deleteHabit(
            BufferedReader reader,
            HabitService habitService,
            User user
    ) throws IOException, SQLException {
        System.out.println("------------------------------------------------------------------------");
        showHabits(habitService.getAllHabitsByUserId(user.getId()));
        System.out.println("Введите название привычки, которую вы хотите удалить: ");
        String title = reader.readLine();
        try {
            Habit habit = habitService.get(user.getId(), title);
            habitService.delete(user.getId(), habit.getTitle(), habit.getId());
            System.out.println("Успешно!");
        } catch (IllegalArgumentException e) {
            System.out.println("Привычки с таким названием не существует!");
        }
        System.out.println("------------------------------------------------------------------------");
    }
}
