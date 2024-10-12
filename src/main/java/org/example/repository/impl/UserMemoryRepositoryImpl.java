package org.example.repository.impl;

import org.example.model.User;
import org.example.repository.UserMemoryRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Хранилище пользователей.
 */
public class UserMemoryRepositoryImpl implements UserMemoryRepository {

    /**
     * Используется для генерации id для пользователя.
     */
    private static Long counter = 100L;

    /**
     * Хранилище пользователей, где ключ - уникальная почта пользователя, а значение - пользователь.
     */
    private final Map<String, User> users = new HashMap<>();

    /**
     * Создает пользователя.
     * Устанавливает значение id с помощью counter.
     * @param user
     * @return
     */
    @Override
    public User create(User user) {
        user.setId(counter++);
        users.put(user.getEmail(), user);
        return user;
    }

    /**
     * Обновляет пользователя по старой почте.
     * @param user
     * @param oldEmail
     * @return
     */
    @Override
    public User update(User user, String oldEmail) {
        users.remove(oldEmail);
        users.put(user.getEmail(), user);
        return user;
    }

    /**
     * Удаляет пользователя по его почте.
     * @param user
     */
    @Override
    public void delete(User user) {
        users.remove(user.getEmail());
    }

    /**
     * Возвращает Optional пользователя по его почте.
     * @param email
     * @return
     */
    @Override
    public Optional<User> get(String email) {
        return Optional.ofNullable(users.get(email));
    }

}
