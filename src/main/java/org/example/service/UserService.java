package org.example.service;

import org.example.model.User;

/**
 * Интерфейс сервиса пользователей.
 */
public interface UserService {

    /**
     * Создает пользователя.
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
     * Возвращает пользователя по его почте.
     * Если пользователя с такой почтой не существует, выкинет ошибку IllegalArgumentException.
     * @param email
     * @return
     */
    User get(String email);

}
