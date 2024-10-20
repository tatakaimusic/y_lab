package org.example.service.impl;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;

import java.sql.SQLException;
import java.util.Objects;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) throws SQLException {
        Objects.requireNonNull(user, "User must not be null");
        if (userRepository.get(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        user = userRepository.create(user).orElseThrow(
                () -> new SQLException("Could not create user")
        );
        return user;
    }

    public User update(User user) throws SQLException {
        Objects.requireNonNull(user, "User must not be null");
        if (userRepository.get(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        return userRepository.update(user);
    }

    public void delete(User user) throws SQLException {
        Objects.requireNonNull(user, "User must not be null");
        userRepository.delete(user);
    }

    public User get(String email) throws SQLException {
        return userRepository.get(email)
                .orElseThrow(
                        () -> new IllegalArgumentException("User with this email does not exist")
                );
    }

}
