package org.example.repository;

import org.example.model.User;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Хранилище пользователей.
 */
public interface UserRepository {

    /**
     * Создает пользователя.
     * Устанавливает значение id с помощью counter.
     * @param user
     * @return
     */
    Optional<User> create(User user) throws SQLException;

    /**
     * Обновляет пользователя.
     * @param user
     * @return
     */
    User update(User user) throws SQLException;

    /**
     * Удаляет пользователя по его почте.
     * @param user
     */
    void delete(User user) throws SQLException;

    /**
     * Возвращает Optional пользователя по его почте.
     * @param email
     * @return
     */
    Optional<User> get(String email) throws SQLException;

    /**
     * Удаляет всех пользователей.
     * @throws SQLException
     */
    void clear() throws SQLException;

}
