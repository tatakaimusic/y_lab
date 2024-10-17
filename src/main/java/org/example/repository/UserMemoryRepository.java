package org.example.repository;

import org.example.model.User;

import java.util.Optional;

/**
 * Хранилище пользователей.
 */
public interface UserMemoryRepository {

    /**
     * Создает пользователя.
     * Устанавливает значение id с помощью counter.
     * @param user
     * @return
     */
    User create(User user);

    /**
     * Обновляет пользователя по старой почте.
     * @param user
     * @param oldEmail
     * @return
     */
    User update(User user, String oldEmail);

    /**
     * Удаляет пользователя по его почте.
     * @param user
     */
    void delete(User user);

    /**
     * Возвращает Optional пользователя по его почте.
     * @param email
     * @return
     */
    Optional<User> get(String email);

}
