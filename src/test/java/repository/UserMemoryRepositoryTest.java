package repository;

import org.example.model.User;
import org.example.repository.impl.UserMemoryRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class UserMemoryRepositoryTest {

    UserMemoryRepositoryImpl userMemoryRepository = new UserMemoryRepositoryImpl();

    @BeforeEach
    void init() {
        User user = new User("testUser", "testEmail", "testPassword");
        userMemoryRepository.create(user);
    }

    @Test
    void create() {
        User user = new User("testUser", "newEmail", "testPassword");
        userMemoryRepository.create(user);
        Assertions.assertTrue(userMemoryRepository.get("newEmail").isPresent());
    }

    @Test
    void getExisted() {
        User user = new User("testUser", "testEmail", "testPassword");
        userMemoryRepository.create(user);
        Assertions.assertTrue(userMemoryRepository.get("testEmail").isPresent());
    }

    @Test
    void getNotExisted() {
        Assertions.assertTrue(userMemoryRepository.get("newEmail").isEmpty());
    }

    @Test
    void update() {
        User user = userMemoryRepository.get("testEmail").orElse(null);
        String oldEmail = user.getEmail();
        user.setEmail("new email");
        userMemoryRepository.update(user, oldEmail);
        User updatedUser = userMemoryRepository.get("new email").orElse(null);
        Assertions.assertEquals(user, updatedUser);
    }

    @Test
    void delete() {
        User user = userMemoryRepository.get("testEmail").orElse(null);
        userMemoryRepository.delete(Objects.requireNonNull(user));
        Assertions.assertTrue(userMemoryRepository.get("testEmail").isEmpty());
    }

}
