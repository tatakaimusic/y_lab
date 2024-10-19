package org.example.in.console;

import liquibase.exception.LiquibaseException;
import org.example.config.DatabaseConfig;
import org.example.model.User;
import org.example.repository.HabitHistoryRepository;
import org.example.repository.HabitRepository;
import org.example.repository.UserRepository;
import org.example.repository.impl.jdbc.HabitJdbcRepository;
import org.example.repository.impl.jdbc.HabitsHistoryJdbcRepository;
import org.example.repository.impl.jdbc.UserJdbcRepository;
import org.example.service.ExecutorService;
import org.example.service.HabitHistoryService;
import org.example.service.HabitService;
import org.example.service.UserService;
import org.example.service.impl.HabitHistoryServiceImpl;
import org.example.service.impl.HabitServiceImpl;
import org.example.service.impl.UserServiceImpl;
import org.example.sql.ConnectionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Консольный интерфейс
 */
public class ConsoleInterface {

    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;
    private final String prodSchema;
    private final String migrationSchema;

    public ConsoleInterface() {
        DatabaseConfig databaseConfig = DatabaseConfig.getInstance();
        this.dbUrl = databaseConfig.getUrl();
        this.dbUsername = databaseConfig.getUser();
        this.dbPassword = databaseConfig.getPassword();
        this.prodSchema = databaseConfig.getProductionSchema();
        this.migrationSchema = databaseConfig.getMigrationSchema();
    }

    /**
     * Запускает отображение консольного интерфейса.
     *
     * @throws IOException
     */
    public void run() throws IOException, SQLException, LiquibaseException {
        Migration migration = new Migration(dbUrl, dbUsername, dbPassword, prodSchema, migrationSchema);
        migration.migrate();

        ConnectionFactory connectionFactory = () -> DriverManager.getConnection
                (
                        dbUrl + "?currentSchema=" + prodSchema,
                        dbUsername,
                        dbPassword
                );

        HabitRepository habitRepository = new HabitJdbcRepository(connectionFactory);
        HabitHistoryRepository habitHistoryRepository = new HabitsHistoryJdbcRepository(connectionFactory);
        UserRepository userRepository = new UserJdbcRepository(connectionFactory);
        HabitService habitService = new HabitServiceImpl(habitRepository, habitHistoryRepository);
        HabitHistoryService habitHistoryService = new HabitHistoryServiceImpl(habitRepository, habitHistoryRepository);
        UserService userService = new UserServiceImpl(userRepository);

        ExecutorService.execute(habitService, habitHistoryService);

        System.out.println("Добро пожаловать");
        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            ConsoleService consoleService = new ConsoleService();
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
                        case "1" -> consoleService.createHabit(reader, habitService, authenticatedUser.getId());
                        case "2" -> consoleService.showHabitsByUserId(reader, habitService, authenticatedUser.getId());
                        case "3" -> consoleService.changeHabit(reader, authenticatedUser, habitService);
                        case "4" ->
                                consoleService.markHabit(authenticatedUser, reader, habitService, habitHistoryService);
                        case "5" ->
                                consoleService.showHistoryOfHabit(reader, habitHistoryService, habitService, authenticatedUser.getId());
                        case "6" ->
                                consoleService.showStatistic(reader, habitHistoryService, habitService, authenticatedUser);
                        case "7" -> consoleService.deleteHabit(reader, habitService, authenticatedUser);
                        case "8" -> consoleService.showProfile(authenticatedUser);
                        case "9" -> consoleService.changeProfile(reader, authenticatedUser, userService);
                        case "10" -> consoleService.changePassword(reader, authenticatedUser, userService);
                        case "11" -> {
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
