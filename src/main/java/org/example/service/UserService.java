package org.example.service;

import org.example.model.User;

import java.sql.SQLException;

/**
 * Интерфейс сервиса пользователей.
 */
public interface UserService {

    /**
     * Создает пользователя.
     * @param user
     * @return
     */
    User create(User user) throws SQLException;

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
     * Возвращает пользователя по его почте.
     * Если пользователя с такой почтой не существует, выкинет ошибку IllegalArgumentException.
     * @param email
     * @return
     */
    User get(String email) throws SQLException;

}
