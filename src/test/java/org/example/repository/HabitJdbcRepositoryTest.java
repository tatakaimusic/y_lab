package org.example.repository;

import liquibase.exception.LiquibaseException;
import org.example.config.DatabaseConfig;
import org.example.in.console.Migration;
import org.example.model.Habit;
import org.example.repository.impl.jdbc.HabitJdbcRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.*;
import static org.example.HabitTestData.*;
import static org.example.UserTestData.USER_EXISTED_ID;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class HabitJdbcRepositoryTest {

    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15.1-alpine"
    );
    private static HabitRepository habitRepository;
    private static Migration migration;
    private static String url;
    private static String username;
    private static String password;
    private static String prodSchema;
    private static String migrationSchema;

    @BeforeEach
    void beforeEach() throws LiquibaseException {
        postgres.start();
        DatabaseConfig databaseConfig = DatabaseConfig.getInstance();
        url = postgres.getJdbcUrl();
        username = postgres.getUsername();
        password = postgres.getPassword();
        prodSchema = databaseConfig.getProductionSchema();
        migrationSchema = databaseConfig.getMigrationSchema();
        habitRepository = new HabitJdbcRepository(() -> DriverManager.getConnection(url, username, password));
        migration = new Migration(url, username, password, prodSchema, migrationSchema);
        migration.migrate();
    }

    @AfterEach
    void afterEach() {
        postgres.stop();
    }

    @Test
    @DisplayName("Creating habit test")
    void createHabit() throws SQLException {
        Habit createdHabit = habitRepository.create(USER_EXISTED_ID, NEW_HABIT, LocalDate.now());
        assertThat(createdHabit.getTitle())
                .isEqualTo(NEW_HABIT.getTitle());
    }

    @Test
    @DisplayName("Getting habit by title test")
    void getHabitByTitle() throws SQLException {
        Habit habit = habitRepository
                .get(USER_EXISTED_ID, HABIT_EXISTED_TITLE)
                .orElse(null);
        assertThat(habit).isNotNull();
    }

    @Test
    @DisplayName("Getting all habits by userId test")
    void getAllHabitsByUserId() throws SQLException {
        List<Habit> habits = habitRepository.getAllHabitsByUserId(USER_EXISTED_ID);
        assertThat(habits).isEqualTo(getAllHabitsByExistedUser());
    }

    @Test
    @DisplayName("Updating habit test")
    void updateHabit() throws SQLException {
        Habit habit = habitRepository.get(USER_EXISTED_ID, HABIT_EXISTED_TITLE).orElse(null);
        habit.setTitle(HABIT_UPDATED_TITLE);
        habitRepository.update(USER_EXISTED_ID, habit);
        Habit updatedHabit = habitRepository.get(USER_EXISTED_ID, HABIT_UPDATED_TITLE).orElse(null);
        assertThat(updatedHabit).isNotNull();
    }

    @Test
    @DisplayName("Deleting habit test")
    void deleteHabit() throws SQLException {
        habitRepository.delete(USER_EXISTED_ID, HABIT_EXISTED_TITLE);
        Habit deletedHabit = habitRepository.get(USER_EXISTED_ID, HABIT_EXISTED_TITLE).orElse(null);
        assertThat(deletedHabit).isNull();
    }

}
