package org.example.in.Console;

import org.example.model.*;
import org.example.service.impl.HabitHistoryServiceImpl;
import org.example.service.impl.HabitServiceImpl;
import org.example.service.impl.UserServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ConsoleService {

    public void showNavigation(){
        System.out.println("Если вы хотите добавить привычку, введите: Создать привычку");
        System.out.println("Если вы хотите посмотреть список ваших привычек, введите: Привычки");
        System.out.println("Если вы хотите имзенить привычку, введите: Изменить привычку");
        System.out.println("Если вы хотите пометить привычку, введите: Отметить");
        System.out.println("Если вы хотите посмотреть историю привычки, введите: История");
        System.out.println("Еесли вы хотите посмотреть статистику по привычке, введите: Статистика");
        System.out.println("Если вы хотите удалить привычку, введите: Удалить привычку");
        System.out.println("Если вы хотите посмотреть информацию о вашем профиле, введите: Профиль");
        System.out.println("Если вы хотите поменять профиль, введите: Изменить профиль");
        System.out.println("Если вы хотите поменять пароль, введите: Поменять пароль");
        System.out.println("Если вы хотите удалить свой аккаунт, введите: Удалить аккаунт");
        System.out.println("Если вы хотитее выйти из аккаунта, введите: Выход");
    }

    public void populate(
            HabitServiceImpl habitService,
            HabitHistoryServiceImpl historyService,
            UserServiceImpl userService
    ) throws IOException {
        User user = new User("user", "user@mail.ru", "password");
        user = userService.create(user);

        Habit habit = new Habit("title", "description", HabitPeriod.DAILY);
        habit = habitService.create(user.getId(), habit, LocalDate.now().minusDays(10));

        for (int i = 0; i < 10; i++) {
            historyService.create(habit.getId(), LocalDate.now().minusDays(i));
        }

        historyService.mark(habit.getId(), user.getId(), LocalDate.now().minusDays(10));
        historyService.mark(habit.getId(), user.getId(), LocalDate.now().minusDays(9));
        historyService.mark(habit.getId(), user.getId(), LocalDate.now().minusDays(8));
        historyService.mark(habit.getId(), user.getId(), LocalDate.now().minusDays(7));
        historyService.mark(habit.getId(), user.getId(), LocalDate.now().minusDays(6));

        historyService.mark(habit.getId(), user.getId(), LocalDate.now().minusDays(2));
        historyService.mark(habit.getId(), user.getId(), LocalDate.now().minusDays(1));
        historyService.mark(habit.getId(), user.getId(), LocalDate.now());

        Habit habit2 = new Habit("title2", "description", HabitPeriod.DAILY);
        habit = habitService.create(user.getId(), habit2, LocalDate.now().minusDays(9));

    }

    public int registration(BufferedReader reader, UserServiceImpl userService) throws IOException {
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

    public User authentication(BufferedReader reader, UserServiceImpl userService) throws IOException {
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

    public void showHabitsByUserId(BufferedReader reader, HabitServiceImpl habitService, Long userId) throws IOException {
        System.out.println("------------------------------------------------------------------------");
        List<Habit> habits;
        System.out.println("Если вы хотите посмотреть в порядке убывания по дате, введите: По убыванию");
        System.out.println("Если вы хотите посмотреть в порядке возрастания по дате, введите: По возрастанию");
        System.out.println("Если вы хотите посмотреть с ежедневной периодичностью, введите: Ежедневно");
        System.out.println("Если вы хотите посмотреть с еженедельной периодичностью, введите: Еженедельно");
        System.out.println();
        System.out.println("Если хотите вернуться, введите: На главную");
        String action = reader.readLine();
        switch (action) {
            case "По убыванию":
                habits = habitService.getAllHabitsByUserIdOrderedByDate(userId, Order.DESC);
                showHabits(habits);
                break;
            case "По возрастанию":
                habits = habitService.getAllHabitsByUserIdOrderedByDate(userId, Order.ASC);
                showHabits(habits);
                break;
            case "Ежедневно":
                habits = habitService.getAllHabitsByUserIdAndPeriod(userId, HabitPeriod.DAILY);
                showHabits(habits);
                break;
            case "Еженедельно":
                habits = habitService.getAllHabitsByUserIdAndPeriod(userId, HabitPeriod.WEEKLY);
                showHabits(habits);
                break;
            case "На главную":
                break;
        }
        System.out.println("------------------------------------------------------------------------");
    }

    private void showHabits(List<Habit> habits) {
        System.out.println("Ваши привычки: ");
        for (Habit habit : habits) {
            System.out.println(habit);
            System.out.println();
        }
    }

    public void createHabit(BufferedReader reader, HabitServiceImpl habitService, Long userId) throws IOException {
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

    public void showHistoryOfHabit(
            BufferedReader reader,
            HabitHistoryServiceImpl habitHistoryService,
            HabitServiceImpl habitService,
            Long userId
    ) throws IOException {
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

    public void showProfile(User user) {
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Ваш профиль: ");
        System.out.println();

        System.out.println("Ваше имя: " + user.getName());
        System.out.println("Ваша почта: " + user.getEmail());
        System.out.println("------------------------------------------------------------------------");
    }

    public void changeHabit(BufferedReader reader, User authenticatedUser, HabitServiceImpl habitService) throws IOException {
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

    public void markHabit(
            User user,
            BufferedReader reader,
            HabitServiceImpl habitService,
            HabitHistoryServiceImpl habitHistoryService
    ) throws IOException {
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

    public void showStatistic(
            BufferedReader reader,
            HabitHistoryServiceImpl habitHistoryService,
            HabitServiceImpl habitService,
            User user
    ) throws IOException {
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
                    "Прцоент выполнения за данный период: "
                            + habitHistoryService.getPercentOfHabitForPeriod(habit.getId(), periodEnum) + " %"
            );
            System.out.println("------------------------------------------------------------------------");
        } catch (IllegalArgumentException e) {
            System.out.println("Привычки с таким названием не существует");
            System.out.println("------------------------------------------------------------------------");
        }

    }

    public void changeProfile(
            BufferedReader reader,
            User authenticatedUser,
            UserServiceImpl userService
    ) throws IOException {
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
                setNewProfile(name, email, authenticatedUser);
            }
        } else {
            setNewProfile(name, email, authenticatedUser);
        }
    }

    private void setNewProfile(String name, String email, User user) {
        user.setName(name);
        user.setEmail(email);
        System.out.println("Успешно!");
        System.out.println("------------------------------------------------------------------------");
    }

    public boolean deleteProfile(
            BufferedReader reader,
            UserServiceImpl userService,
            User authenticatedUser
    ) throws IOException {
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

    public void changePassword(
            BufferedReader reader,
            User authenticatedUser,
            UserServiceImpl userService
    ) throws IOException {
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Введите старый пароль: ");
        String oldPassword = reader.readLine();
        if (!oldPassword.equals(authenticatedUser.getPassword())) {
            System.out.println("Нерпавильный пароль!");
        } else {
            System.out.println("Введите новый пароль: ");
            String newPassword = reader.readLine();
            authenticatedUser.setPassword(newPassword);
            System.out.println("Успешно!");
        }
        System.out.println("------------------------------------------------------------------------");
    }

    public void deleteHabit(
            BufferedReader reader,
            HabitServiceImpl habitService,
            User user
    ) throws IOException {
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
