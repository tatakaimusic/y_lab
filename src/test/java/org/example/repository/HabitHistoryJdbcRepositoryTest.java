package org.example.repository;

import liquibase.exception.LiquibaseException;
import org.example.config.DatabaseConfig;
import org.example.in.console.Migration;
import org.example.model.HabitHistoryMark;
import org.example.repository.impl.jdbc.HabitsHistoryJdbcRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.example.HabitHistoryTestData.*;
import static org.example.HabitTestData.HABIT_EXISTED_ID;
import static org.assertj.core.api.Assertions.*;

public class HabitHistoryJdbcRepositoryTest {

    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15.1-alpine"
    );
    private static HabitHistoryRepository habitHistoryRepository;
    private static Migration migration;
    private static String url;
    private static String username;
    private static String password;
    private static String prodSchema;
    private static String migrationSchema;

    // Не успел разобраться с тем как делать rollback changesets,
    // поэтому пришлось сделать такой костыль,
    // чтобы контейнер заново каждый раз поднимался. Из за этого долгое тестирование.
    @BeforeEach
    void beforeEach() throws LiquibaseException {
        postgres.start();
        DatabaseConfig databaseConfig = DatabaseConfig.getInstance();
        url = postgres.getJdbcUrl();
        username = postgres.getUsername();
        password = postgres.getPassword();
        prodSchema = databaseConfig.getProductionSchema();
        migrationSchema = databaseConfig.getMigrationSchema();
        habitHistoryRepository = new HabitsHistoryJdbcRepository(
                () -> DriverManager.getConnection(url, username, password)
        );
        migration = new Migration(url, username, password, prodSchema, migrationSchema);
        migration.migrate();
    }

    @AfterEach
    void afterEach() {
        postgres.stop();
    }

    @Test
    @DisplayName("Marking habit test")
    void mark() throws SQLException {
        habitHistoryRepository.mark(HABIT_EXISTED_ID, HISTORY_EXISTED_DATE);
        Boolean mark = habitHistoryRepository.getLocalDateMark(HABIT_EXISTED_ID, HISTORY_EXISTED_DATE);
        assertThat(!mark)
                .isEqualTo(HISTORY_EXISTED_MARK);
    }

    @Test
    @DisplayName("Creating mark test")
    void create() throws SQLException {
        habitHistoryRepository.create(HABIT_EXISTED_ID, LocalDate.now());
        Boolean mark = habitHistoryRepository.getLocalDateMark(HABIT_EXISTED_ID, LocalDate.now());
        assertThat(mark)
                .isEqualTo(false);
    }

    @Test
    @DisplayName("Deleting history test")
    void delete() throws SQLException {
        habitHistoryRepository.delete(HABIT_EXISTED_ID);
        List<HabitHistoryMark> history = habitHistoryRepository.getHabitHistory(HABIT_EXISTED_ID);
        assertThat(history.isEmpty())
                .isTrue();
    }

    @Test
    @DisplayName("Getting habit history test")
    void getHabitHistory() throws SQLException {
        List<HabitHistoryMark> history = habitHistoryRepository.getHabitHistory(HABIT_EXISTED_ID);
        assertThat(history.size())
                .isEqualTo(EXISTED_HISTORY_SIZE);
    }
}
