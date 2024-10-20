package org.example.repository;

import liquibase.exception.LiquibaseException;
import org.example.config.DatabaseConfig;
import org.example.in.console.Migration;
import org.example.model.User;
import org.example.repository.impl.jdbc.UserJdbcRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static org.example.UserTestData.*;


public class UserJdbcRepositoryTest {

    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15.1-alpine"
    );
    private static UserRepository userRepository;
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
        userRepository = new UserJdbcRepository(() -> DriverManager.getConnection(url, username, password));
        migration = new Migration(url, username, password, prodSchema, migrationSchema);
        migration.migrate();
    }

    @AfterEach
    void afterEach() {
        postgres.stop();
    }

    @Test
    @DisplayName("Creating user test")
    void create() throws SQLException {
        User createdUser = userRepository.create(NEW_USER).orElse(null);
        assertThat(createdUser.getEmail())
                .isEqualTo(NEW_USER.getEmail());
    }

    @Test
    @DisplayName("Updating user test")
    void update() throws SQLException {
        User user = userRepository.get(EXISTED_USER.getEmail()).orElse(null);
        user.setEmail(UPDATED_EMAIL);
        User updatedUser = userRepository.update(user);
        assertThat(updatedUser.getEmail())
                .isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @DisplayName("Deleting user test")
    void delete() throws SQLException {
        userRepository.delete(EXISTED_USER);
        User deletedUser = userRepository.get(EXISTED_USER.getEmail()).orElse(null);
        assertThat(deletedUser).isNull();
    }

    @Test
    @DisplayName("Get existed user test")
    void getExisted() throws SQLException {
        User user = userRepository.get(EXISTED_USER.getEmail()).orElse(null);
        assertThat(user).isNotNull();
    }

    @Test
    @DisplayName("Get not existed user test")
    void getNotExisted() throws SQLException {
        User user = userRepository.get(NEW_USER.getEmail()).orElse(null);
        assertThat(user).isNull();
    }

}
